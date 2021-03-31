package msgpack4z

trait Java07Spec { self: SpecBase =>
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
