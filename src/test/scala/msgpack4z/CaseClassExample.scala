package msgpack4z

import msgpack4z.AsTuple._
import msgpack4z.CodecInstances.all._
import scalaprops.Property.forAll
import scalaprops._
import scalaz.\/

object CaseClassExample extends Scalaprops {
  case class Foo(a: Boolean, b: String, c: List[Int])

  val sample = Foo(true, "abcde", List(10, 20, 30))

  val `case class map example` = forAll {
    val factory = new PackerUnpackerFactory {
      def packer: MsgPacker = MsgOutBuffer.create()
      def unpacker(bytes: Array[Byte]): MsgUnpacker = MsgInBuffer(bytes)
    }

    val mapCodec = CaseMapCodec.string(factory)

    val instance = mapCodec.codec(Foo.apply _, (x: Foo) => Some(x.asTuple))("a", "b", "c")

    val bytes = instance.toBytes(sample, MsgOutBuffer.create())
    val union = MsgpackCodec[MsgpackUnion].unpackAndClose(MsgInBuffer(bytes))

    assert(
      union == \/.right(
        MsgpackUnion.map(
          Map(
            MsgpackUnion.string("a") -> MsgpackUnion.True,
            MsgpackUnion.string("b") -> MsgpackUnion.string("abcde"),
            MsgpackUnion.string("c") -> MsgpackUnion.array(
              List(10, 20, 30).map(MsgpackUnion.int)
            )
          )
        )
      )
    )

    instance.unpackAndClose(MsgInBuffer(bytes)) == \/.right(sample)
  }

  val `case class array example` = forAll {
    val instance = CaseCodec.codec(Foo.apply _, (x: Foo) => Some(x.asTuple))

    val bytes = instance.toBytes(sample, MsgOutBuffer.create())
    val union = MsgpackCodec[MsgpackUnion].unpackAndClose(MsgInBuffer(bytes))

    assert(
      union == \/.right(
        MsgpackUnion.array(
          List(
            MsgpackUnion.True,
            MsgpackUnion.string("abcde"),
            MsgpackUnion.array(
              List(10, 20, 30).map(MsgpackUnion.int)
            )
          )
        )
      )
    )

    instance.unpackAndClose(MsgInBuffer(bytes)) == \/.right(sample)
  }

}
