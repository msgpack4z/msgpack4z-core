package msgpack4z

trait Java06Spec{ _: SpecBase =>
  override protected[this] def packer() = Msgpack06.defaultPacker()
  override protected[this] def unpacker(bytes: Array[Byte]) = Msgpack06.defaultUnpacker(bytes)
}

object Java06ArraySpec extends ArraySpec with Java06Spec
object Java06StdSpec extends StdSpec with Java06Spec
object Java06BinarySpec extends BinarySpec with Java06Spec
object Java06ScalazSpec extends ScalazSpec with Java06Spec
object Java06UIntSpec extends UIntSpec with Java06Spec
object Java06IntSpec extends IntSpec with Java06Spec
object Java06UnionSpec extends UnionSpec(UnionGen.unionWithoutExtGen) with Java06Spec



trait Java07Spec{ _: SpecBase =>
  override protected[this] def packer() = new Msgpack07Packer()
  override protected[this] def unpacker(bytes: Array[Byte]) = Msgpack07Unpacker.defaultUnpacker(bytes)
}

object Java07ArraySpec extends ArraySpec with Java07Spec
object Java07StdSpec extends StdSpec with Java07Spec
object Java07BinarySpec extends BinarySpec with Java07Spec
object Java07ScalazSpec extends ScalazSpec with Java07Spec
object Java07UIntSpec extends UIntSpec with Java07Spec
object Java07IntSpec extends IntSpec with Java07Spec
object Java07UnionSpec extends UnionSpec with Java07Spec



trait NativeSpec{ _: SpecBase =>
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
