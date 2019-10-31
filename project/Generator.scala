import sbt._, Keys._

object Generator {
  private val generateCode = TaskKey[Unit]("generateCode")
  private val generateFiles = SettingKey[Seq[GeneratedCode]]("generateFiles")
  private val checkGenerateCode = TaskKey[Boolean]("checkGenerateCode")
  private val checkGenerateCodeError = TaskKey[Unit]("checkGenerateCodeError")

  private final case class GeneratedCode(file: File, code: String) {
    def write(): Unit = IO.write(file, code)
    def check: Boolean = {
      if (file.isFile) {
        IO.read(file) == code
      } else {
        println(red(file + " not found!"))
        false
      }
    }
  }

  private def red(str: String) = {
    ("\n" * 2) + scala.Console.RED + str + ("\n " * 2) + scala.Console.RESET
  }

  val settings: Seq[Def.Setting[_]] = Seq(
    generateFiles := {
      val pack = "msgpack4z"
      val dir = CustomCrossType.shared(baseDirectory.value, "main") / pack
      val caseCodec = dir / "CaseCodec.scala"
      val caseMapCodec = dir / "CaseMapCodec.scala"
      val arrayCodec = dir / "AnyValArrayCodec.scala"
      val tupleCodec = dir / "TupleCodec.scala"
      val anyValCodec = dir / "AnyValCodec.scala"
      List(
        GeneratedCode(caseCodec, CaseCodec.generate(pack)),
        GeneratedCode(caseMapCodec, CaseMapCodec.generate(pack)),
        GeneratedCode(arrayCodec, ArrayCodec.generate(pack)),
        GeneratedCode(tupleCodec, TupleCodec.generate(pack)),
        GeneratedCode(anyValCodec, AnyValCodec.generate(pack))
      )
    },
    generateCode := generateFiles.value.foreach(_.write),
    checkGenerateCode := generateFiles.value.forall(_.check),
    checkGenerateCodeError := {
      generateCode.value
      Thread.sleep(1000)
      val diff = scala.sys.process.Process("git diff").!!
      if (diff.nonEmpty) {
        sys.error("Working directory is dirty!\n" + diff)
      }
    },
    shellPrompt := { state =>
      val extracted = Project.extract(state)
      if (extracted.runTask(checkGenerateCode, state)._2) {
        shellPrompt.?.value.map(_.apply(state)).getOrElse("")
      } else {
        red("generator code changed. please execute " + generateCode.key.label)
      }
    }
  )
}
