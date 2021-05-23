package msgpack4z

import msgpack4z.CodecInstances.all._
import scalaprops.Property.forAll
import scalaprops._
import scalaz.\/

object CaseClassExample2 extends Scalaprops {
  final case class UserId(value: Long)
  object UserId extends MsgpackCompanion[Long, UserId]

  val `single parameter case class use MsgpackCompanion` = forAll {
    val userId = UserId(42)

    val bytes = MsgpackCodec[UserId].toBytes(userId, MsgOutBuffer.create())
    val union = MsgpackCodec[MsgpackUnion].unpackAndClose(MsgInBuffer(bytes))
    assert(union == \/.right(MsgpackUnion.long(userId.value)))

    MsgpackCodec[UserId].unpackAndClose(MsgInBuffer(bytes)) == \/.right(userId)
  }
}
