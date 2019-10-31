object CaseCodec {
  private def tparamList(i: Int) = (1 to i).map("A" + _)

  private final val left = """-\/""" // avoid InvalidEscapeException

  private final val construct = "construct"
  private final val extract = "extract"
  private final val Z = "Z"

  private[this] val f: Int => String = { i =>
    val tparams = tparamList(i)
    val tparams1 = tparams.mkString(", ")
    val implicitParams = tparams.map(x => x + ": MsgpackCodec[" + x + "]").mkString(", ")
    val pack = tparams.zipWithIndex.map { case (t, n) => t + ".pack(packer, x._" + (n + 1) + ")" }.mkString("; ")
    val unpack = tparams.map(_ + ".unpack(unpacker)").mkString(", ")
    val methodName = "codec" + i
    def methodDef(name: String) =
      s"""def $name[$tparams1, $Z]($construct: ($tparams1) => $Z, $extract: $Z => Option[($tparams1)])(implicit $implicitParams): MsgpackCodec[$Z] ="""

    s"""
  ${methodDef(methodName)}
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader($i)
        val x = $extract(z).get
        $pack
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == $i) {
          val result = zeroapply.DisjunctionApply.apply$i(
            $unpack
          )($construct)
          unpacker.arrayEnd()
          result
        }else{
          $left(new UnexpectedArraySize($i, size))
        }
      }
    )

  ${methodDef("codec")}
    $methodName($construct, $extract)($tparams1)
"""
  }

  private[this] def one = {
    val codec1 = "codec1"
    val A1 = "A1"
    def methodDef(name: String) =
      s"""def $name[$A1, Z]($construct: $A1 => Z, $extract: Z => Option[$A1])(implicit $A1: MsgpackCodec[$A1]): MsgpackCodec[Z] ="""

    s"""
  ${methodDef(codec1)}
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(1)
        A1.pack(packer, $extract(z).get)
        packer.arrayEnd()
      }
      ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 1) {
          A1.unpack(unpacker).map($construct)
        }else{
          $left(new UnexpectedArraySize(1, size))
        }
      }
    )

  ${methodDef("codec")}
    $codec1($construct, $extract)($A1)
"""
  }

  def generate(pack: String): String = {
    s"""package $pack

import scalaz.$left

// GENERATED CODE: DO NOT EDIT.
object CaseCodec {
$one

${(2 to 22).map(f).mkString("\n")}
}
"""
  }
}
