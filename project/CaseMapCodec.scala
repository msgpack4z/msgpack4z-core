object CaseMapCodec {
  private val K = "K"
  private val mapCodec = "mapCodec"
  private val factory = "factory"
  private val c = "MsgpackCodec"
  private val right = """\/-"""
  private val left = """-\/"""

  class Method(n: Int) {
    private val Z = "Z"
    private val apply = "apply"
    private val unapply = "unapply"
    private val params0 = (1 to n).map("a" + _)
    private val tparams0 = (1 to n).map("A" + _)
    private val tparams1 = tparams0.mkString(", ")
    private val implicits = tparams0.map(a => s"$a: $c[$a]").mkString("implicit ", ", ", "")

    def methods: String = {
      val name = "codec" + n
      method(name) + "\n" + overload(name)
    }

    private def signature(name: String) = {
      val implicits = tparams0.map(a => s"$a: $c[$a]").mkString("implicit ", ", ", "")
      s"""  def $name[$tparams1, $Z]($apply: ($tparams1) => $Z, $unapply: $Z => Option[($tparams1)])(${params0
        .map(_ + ": " + K)
        .mkString(", ")})($implicits): $c[$Z] ="""
    }

    private def overload(name: String) = {
      signature("codec") + s"""
    $name($apply, $unapply)(${params0.mkString(", ")})($tparams1)"""
    }

    private def method(name: String) = {
      val packer = "packer"
      val unpacker = "unpacker"
      val value = "value"
      val t = "t"
      val keys = "keys"

      s"""
${signature(name)}
    $c.codec[$Z](
    ($packer, $value) => {
      $packer.packMapHeader($n)
      val $t = $unapply($value).get
      ${(0 until n).map { i =>
        s"""$K.pack($packer, ${params0(i)}); ${tparams0(i)}.pack($packer, $t._${i + 1})"""
      }.mkString("; ")}
      $packer.mapEnd()
    },
    $unpacker => $mapCodec.unpack($unpacker) match {
      case $right($value) =>
        ${(0 until n).map(i => s"val b${i + 1} = value.get(${params0(i)})").mkString("; ")}
        var $keys: IList[K] = IList.empty
        ${(n to 1 by -1).map(i => s"if(b$i.isEmpty){ $keys = new ICons(a$i, $keys) }").mkString("\n        ")}

        $keys match {
          case ICons(h, t) =>
            $left(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), $K)))
          case _ =>
            zeroapply.DisjunctionApply.apply$n(
              ${(0 until n).map(i => s"b${i + 1}.get.as[${tparams0(i)}]($factory)(${tparams0(i)})").mkString(", ")}
            )($apply)
        }
      case e @ $left(_) => e
    }
  )
"""
    }
  }

  def generate(pack: String): String = {
    val codec1 = """
  def codec1[A1, Z](apply: A1 => Z, unapply: Z => Option[A1])(a1: K)(implicit A1: MsgpackCodec[A1]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(1)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1)
        var keys: IList[K] = IList.empty
        if(b1.isEmpty){ keys = new ICons(a1, keys) }

        keys match {
          case ICons(h, t) =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            b1.get.as[A1](factory)(A1).map(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, Z](apply: A1 => Z, unapply: Z => Option[A1])(a1: K)(implicit A1: MsgpackCodec[A1]): MsgpackCodec[Z] =
    codec1(apply, unapply)(a1)(A1)"""

    s"""package $pack

import scalaz._

// GENERATED CODE: DO NOT EDIT.
object CaseMapCodec {

  def string(factory: PackerUnpackerFactory) =
    new CaseMapCodec[String](factory)(CodecInstances.std.stringCodec)

  def int(factory: PackerUnpackerFactory) =
    new CaseMapCodec[Int](factory)(CodecInstances.anyVal.intCodec)
}

class CaseMapCodec[$K]($factory: PackerUnpackerFactory)(implicit K: $c[K]) {

  private[this] val $mapCodec =
    msgpack4z.CodecInstances.std.mapCodec[K, MsgpackUnion]
${codec1}
${(2 to 22).map { n =>
      new Method(n).methods
    }.mkString("\n")}
}
"""
  }
}
