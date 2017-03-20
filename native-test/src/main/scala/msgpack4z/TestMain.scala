package msgpack4z

import msgpack4z.CodecInstances.all._
import scalaz.\/

object TestMain {

  def main(args: Array[String]): Unit = {
    tests1.foreach { case (name, test) =>
      println(name)
      assert(test.apply(), name)
    }
  }

  case class Foo(a: Boolean, b: String, c: List[Int])

  final case class UserId(value: Long)

  object UserId extends MsgpackCompanion[Long, UserId]

  val sample = Foo(true, "abcde", List(10, 20, 30))

  val tests1 = Seq[(String, () => Boolean)](
    "case class map example" -> { () =>
      val factory = new PackerUnpackerFactory {
        def packer = MsgOutBuffer.create()
        def unpacker(bytes: Array[Byte]) = MsgInBuffer(bytes)
      }

      val mapCodec = CaseMapCodec.string(factory)

      val instance = mapCodec.codec(Foo.apply _, Foo.unapply _)("a", "b", "c")

      val bytes = instance.toBytes(sample, MsgOutBuffer.create())
      val union = MsgpackCodec[MsgpackUnion].unpackAndClose(MsgInBuffer(bytes))

      assert(union == \/.right(MsgpackUnion.map(
        Map(
          MsgpackUnion.string("a") -> MsgpackUnion.True,
          MsgpackUnion.string("b") -> MsgpackUnion.string("abcde"),
          MsgpackUnion.string("c") -> MsgpackUnion.array(
            List(10, 20, 30).map(MsgpackUnion.int)
          )
        )
      )))

      instance.unpackAndClose(MsgInBuffer(bytes)) == \/.right(sample)
    },
    "case class array example" -> { () =>
      val instance = CaseCodec.codec(Foo.apply _, Foo.unapply _)

      val bytes = instance.toBytes(sample, MsgOutBuffer.create())
      val union = MsgpackCodec[MsgpackUnion].unpackAndClose(MsgInBuffer(bytes))

      assert(union == \/.right(MsgpackUnion.array(
        List(
          MsgpackUnion.True,
          MsgpackUnion.string("abcde"),
          MsgpackUnion.array(
            List(10, 20, 30).map(MsgpackUnion.int)
          )
        )
      )))

      instance.unpackAndClose(MsgInBuffer(bytes)) == \/.right(sample)
    },
    "single parameter case class use MsgpackCompanion" ->  { () =>
      val userId = UserId(42)

      val bytes = MsgpackCodec[UserId].toBytes(userId, MsgOutBuffer.create())
      val union = MsgpackCodec[MsgpackUnion].unpackAndClose(MsgInBuffer(bytes))
      assert(union == \/.right(MsgpackUnion.long(userId.value)))

      MsgpackCodec[UserId].unpackAndClose(MsgInBuffer(bytes)) == \/.right(userId)
    }
  )
}
