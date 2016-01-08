package msgpack4z

trait JavaSpec{ _: SpecBase =>
  override protected[this] def packer() = new MsgpackJavaPacker()
  override protected[this] def unpacker(bytes: Array[Byte]) = MsgpackJavaUnpacker.defaultUnpacker(bytes)
}

object JavaArraySpec extends ArraySpec with JavaSpec
object JavaStdSpec extends StdSpec with JavaSpec
object JavaBinarySpec extends BinarySpec with JavaSpec
object JavaScalazSpec extends ScalazSpec with JavaSpec
object JavaUIntSpec extends UIntSpec with JavaSpec
object JavaIntSpec extends IntSpec with JavaSpec
object JavaUnionSpec extends UnionSpec with JavaSpec
