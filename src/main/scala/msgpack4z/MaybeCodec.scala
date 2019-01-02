package msgpack4z

import scalaz.{-\/, \/, \/-, Maybe}

object MaybeCodec {

  private[this] val RightEmpty = \/-(Maybe.Empty())
  private[this] def rightEmpty[A]: UnpackError \/ Maybe[A] = RightEmpty.asInstanceOf[UnpackError \/ Maybe[A]]

  final def maybeCodec[A, B](JustKey: B, EmptyKey: B)(implicit A: MsgpackCodec[A], B: MsgpackCodec[B]): MsgpackCodec[Maybe[A]] = {
    val HeaderSize = 1
    MsgpackCodec.tryE(
      (packer, maybe) => {
        packer.packMapHeader(HeaderSize)
        maybe match {
          case Maybe.Just(a) =>
            B.pack(packer, JustKey)
            A.pack(packer, a)
          case _ =>
            B.pack(packer, EmptyKey)
            packer.packNil()
        }
        packer.mapEnd()
      },
      unpacker => {
        val size = unpacker.unpackMapHeader()
        if (size == HeaderSize) {
          val result = B.unpack(unpacker).flatMap {
            case JustKey =>
              A.unpack(unpacker).map(Maybe.Just(_))
            case EmptyKey =>
              unpacker.unpackNil()
              rightEmpty[A]
            case _ =>
              -\/(MapKeyNotFound(JustKey.toString, EmptyKey.toString))
          }
          unpacker.mapEnd()
          result
        } else {
          -\/(new UnexpectedMapSize(HeaderSize, size))
        }
      }
    )
  }

  final def maybeCompactCodec[A](implicit A: MsgpackCodec[A]): MsgpackCodec[Maybe[A]] = {
    import CodecInstances.anyVal.byteCodec
    maybeCodec[A, Byte](1, 0)
  }

  final def maybeStringCodec[A](implicit A: MsgpackCodec[A]): MsgpackCodec[Maybe[A]] = {
    import CodecInstances.std.stringCodec
    maybeCodec[A, String]("just", "empty")
  }

}
