package msgpack4z

trait Java06Spec{ _: SpecBase =>
  override protected[this] def packer() = Msgpack06.defaultPacker()
  override protected[this] def unpacker(bytes: Array[Byte]) = Msgpack06.defaultUnpacker(bytes)
}

object Java06ArraySpec extends ArraySpec("java06") with Java06Spec
object Java06StdSpec extends StdSpec("java06") with Java06Spec
object Java06BinarySpec extends BinarySpec("java06") with Java06Spec
object Java06ScalazSpec extends ScalazSpec("java06") with Java06Spec
object Java06UIntSpec extends UIntSpec("java06") with Java06Spec
object Java06IntSpec extends IntSpec("java06") with Java06Spec



trait Java07Spec{ _: SpecBase =>
  override protected[this] def packer() = new Msgpack07Packer()
  override protected[this] def unpacker(bytes: Array[Byte]) = Msgpack07Unpacker.defaultUnpacker(bytes)
}

object Java07ArraySpec extends ArraySpec("java07") with Java07Spec
object Java07StdSpec extends StdSpec("java07") with Java07Spec
object Java07BinarySpec extends BinarySpec("java07") with Java07Spec
object Java07ScalazSpec extends ScalazSpec("java07") with Java07Spec
object Java07UIntSpec extends UIntSpec("java07") with Java07Spec
object Java07IntSpec extends IntSpec("java07") with Java07Spec



trait NativeSpec{ _: SpecBase =>
  override protected[this] def packer() = MsgOutBuffer.create()
  override protected[this] def unpacker(bytes: Array[Byte]) = MsgInBuffer(bytes)
}


object NativeArraySpec extends ArraySpec("native") with NativeSpec
object NativeStdSpec extends StdSpec("native") with NativeSpec
object NativeBinarySpec extends BinarySpec("native") with NativeSpec
object NativeScalazSpec extends ScalazSpec("native") with NativeSpec
object NativeUIntSpec extends UIntSpec("native") with NativeSpec
object NativeIntSpec extends IntSpec("native") with NativeSpec
