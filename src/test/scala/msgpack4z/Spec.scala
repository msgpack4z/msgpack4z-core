package msgpack4z

trait NativeSpec { self: SpecBase =>
  override protected[this] def packer() = MsgOutBuffer.create()
  override protected[this] def unpacker(bytes: Array[Byte]) = MsgInBuffer(bytes)
}

object NativeArraySpec extends ArraySpec with NativeSpec
object NativeStdSpec extends StdSpec with NativeSpec
object NativeBinarySpec extends BinarySpec with NativeSpec
object NativeScalazSpec extends ScalazSpec with NativeSpec
object NativeUIntSpec extends UIntSpec with NativeSpec
object NativeIntSpec extends IntSpec with NativeSpec
object NativeUnionSpec extends UnionSpec with NativeSpec
