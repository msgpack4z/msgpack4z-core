object TupleCodec {
  private def tparams(i: Int) = (1 to i).map("A" + _)
  private def ts(i: Int) = tparams(i).mkString(", ")
  private val defdef: Int => String = { i =>
    s"""implicit def tuple${i}Codec[${ts(i)}](implicit ${tparams(i)
      .map(x => x + ": MsgpackCodec[" + x + "]")
      .mkString(", ")}): MsgpackCodec[Tuple$i[${ts(i)}]]"""
  }

  private val f: Int => String = { i =>
    s"""  final override ${defdef(i)} =
    CaseCodec.codec$i[${ts(i)}, Tuple$i[${ts(i)}]](Tuple$i.apply, Tuple$i.unapply)
"""
  }

  def generate(pack: String) = {
    s"""package $pack

// GENERATED CODE: DO NOT EDIT.
trait TupleCodec {
${(1 to 22 map defdef map ("  " + _)).mkString("\n")}
}

private[$pack] trait TupleCodecImpl extends TupleCodec {

${(1 to 22 map f).mkString("\n")}
}
"""
  }
}
