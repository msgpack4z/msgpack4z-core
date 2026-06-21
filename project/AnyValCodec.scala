import java.util.Locale

object AnyValCodec {
  private val types = "Boolean Byte Short Int Long Float Double".split(' ').toList

  private val defdef = types.map { tpe => s"  implicit def ${tpe.toLowerCase(Locale.ENGLISH)}Codec: MsgpackCodec[$tpe]" }.mkString("\n")

  private val impl = types.map { tpe =>
    s"""
  override final def ${tpe.toLowerCase(Locale.ENGLISH)}Codec: MsgpackCodec[$tpe] =
    MsgpackCodec.tryConst(_.pack$tpe(_), _.unpack$tpe())"""
  }.mkString("\n")

  def generate(pack: String): String = {
    s"""package $pack

// GENERATED CODE: DO NOT EDIT.
trait AnyValCodec {
$defdef
}

private[$pack] trait AnyValCodecImpl extends AnyValCodec {
$impl

}
"""
  }
}
