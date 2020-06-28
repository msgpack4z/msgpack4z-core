object ArrayCodec {
  private val types = "Boolean Byte Short Int Long Float Double".split(' ').toList

  private val nameF = "array" + (_: String)
  private val typeF = "MsgpackCodec[Array[" + (_: String) + "]]"

  private def defdef(t: String): String = nameF(t) + ": " + typeF(t)

  private def f(t: String) = {
    val name = nameF(t)
    val tpe = typeF(t)
    s"""
  implicit final override val ${defdef(t)} = MsgpackCodec.tryConst(
    { (packer, array) =>
      packer.packArrayHeader(array.length)
      var i = 0
      while (i < array.length) {
        packer.pack$t(array(i))
        i += 1
      }
      packer.arrayEnd()
    },
    { unpacker =>
      val size = unpacker.unpackArrayHeader()
      val array = new Array[$t](size)
      var i = 0
      while(i < size){
        array(i) = unpacker.unpack$t()
        i += 1
      }
      unpacker.arrayEnd()
      array
    }
  )
"""
  }

  def single(pack: String, t: String) = {
    val c = t + "ArrayCodec"
    s"""
trait $c {
  implicit def ${defdef(t)}
}

private[$pack] object ${c}Impl extends $c {
  implicit override val ${defdef(t)} = CodecInstances.all.${nameF(t)}
}
"""
  }

  def generate(pack: String): String = {
    val implicitDef = types.map { t =>
      {
        if (t == "Byte") "  "
        else "  implicit "
      } + "def " + defdef(t)
    }.mkString("\n")

    s"""package $pack

// GENERATED CODE: DO NOT EDIT.
trait AnyValArrayCodec {
$implicitDef
}

private[$pack] trait AnyValArrayCodecImpl extends AnyValArrayCodec {

${types.map(f).mkString("\n")}

}

${types.map(single(pack, _)).mkString("\n")}"""
  }
}
