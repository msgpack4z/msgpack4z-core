package msgpack4z

import scalaz.{-\/, \/, \/-}

object OptionCodec {
  private[this] val RightNone: UnpackError \/- Option[Nothing] = new \/-(None)

  final def optionCodec[A, B](SomeKey: B, NoneKey: B)(implicit A: MsgpackCodec[A], B: MsgpackCodec[B]): MsgpackCodec[Option[A]] = {
    val HeaderSize = 1
    MsgpackCodec.tryE(
      (packer, opt) => {
        packer.packMapHeader(HeaderSize)
        opt match {
          case Some(a) =>
            B.pack(packer, SomeKey)
            A.pack(packer, a)
          case None =>
            B.pack(packer, NoneKey)
            packer.packNil()
        }
        packer.mapEnd()
      },
      unpacker => {
        val size = unpacker.unpackMapHeader()
        if (size == HeaderSize) {
          val result = B.unpack(unpacker).flatMap[Option[A]] {
            case SomeKey =>
              A.unpack(unpacker).map(Some(_))
            case NoneKey =>
              unpacker.unpackNil()
              RightNone.asInstanceOf[UnpackError \/ Option[A]]
            case _ =>
              -\/(MapKeyNotFound(SomeKey.toString, NoneKey.toString))
          }
          unpacker.mapEnd()
          result
        } else {
          -\/(new UnexpectedMapSize(HeaderSize, size))
        }
      }
    )
  }

  final def optionCompactCodec[A](implicit A: MsgpackCodec[A]): MsgpackCodec[Option[A]] = {
    import CodecInstances.anyVal.byteCodec
    optionCodec[A, Byte](1, 0)
  }

  final def optionStringCodec[A](implicit A: MsgpackCodec[A]): MsgpackCodec[Option[A]] = {
    import CodecInstances.std.stringCodec
    optionCodec[A, String]("some", "none")
  }
}
