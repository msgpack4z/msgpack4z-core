package msgpack4z

import org.scalacheck.Properties
import msgpack4z.CodecInstances.all._
import scalaz.\/

object CaseClassExample extends Properties("case class example"){

  case class Foo(a: Boolean, b: String, c: List[Int])

  val sample = Foo(true, "abcde", List(10, 20, 30))

  property("case class map example") = {

    val java07 = new PackerUnpackerFactory {
      def packer = new Msgpack07Packer()
      def unpacker(bytes: Array[Byte]) = Msgpack07Unpacker.defaultUnpacker(bytes)
    }

    val mapCodec = CaseMapCodec.string(java07)

    val instance = mapCodec.codec(Foo.apply _, Foo.unapply _)("a", "b", "c")

    val bytes = instance.toBytes(sample, new Msgpack07Packer())
    val union = MsgpackCodec[MsgpackUnion].unpackAndClose(Msgpack07Unpacker.defaultUnpacker(bytes))

    assert(union == \/.right(MsgpackUnion.map(
      Map(
        MsgpackUnion.string("a") -> MsgpackUnion.True,
        MsgpackUnion.string("b") -> MsgpackUnion.string("abcde"),
        MsgpackUnion.string("c") -> MsgpackUnion.array(
          List(10, 20, 30).map(MsgpackUnion.int)
        )
      )
    )))

    instance.unpackAndClose(Msgpack07Unpacker.defaultUnpacker(bytes)) == \/.right(sample)
  }

  property("case class array example") = {

    val instance = CaseCodec.codec(Foo.apply _, Foo.unapply _)

    val bytes = instance.toBytes(sample, new Msgpack07Packer())
    val union = MsgpackCodec[MsgpackUnion].unpackAndClose(Msgpack07Unpacker.defaultUnpacker(bytes))

    assert(union == \/.right(MsgpackUnion.array(
      List(
        MsgpackUnion.True,
        MsgpackUnion.string("abcde"),
        MsgpackUnion.array(
          List(10, 20, 30).map(MsgpackUnion.int)
        )
      )
    )))

    instance.unpackAndClose(Msgpack07Unpacker.defaultUnpacker(bytes)) == \/.right(sample)
  }
}
