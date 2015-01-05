package msgpack4z

import scalaz.-\/

object EitherCodec {
  private[this] final val headerSize = 1

  def eitherCodec[A, B, X](LeftKey: X, RightKey: X)(implicit A: MsgpackCodec[A], B: MsgpackCodec[B], X: MsgpackCodec[X]): MsgpackCodec[Either[A, B]] =
    eitherCodecImpl[A, B, X](LeftKey, RightKey)

  implicit def eitherStringCodec[A: MsgpackCodec, B: MsgpackCodec]: MsgpackCodec[Either[A, B]] = {
    import CodecInstances.std.stringCodec
    eitherCodec[A, B, String]("left", "right")
  }

  implicit def eitherCompactCodec[A: MsgpackCodec, B: MsgpackCodec]: MsgpackCodec[Either[A, B]] = {
    import CodecInstances.anyVal.byteCodec
    eitherCodec[A, B, Byte](0, 1)
  }

  private[this] def eitherCodecImpl[A, B, X](LeftKey: X, RightKey: X)(implicit A: MsgpackCodec[A], B: MsgpackCodec[B], X: MsgpackCodec[X]): MsgpackCodec[Either[A, B]] =
    MsgpackCodec.tryE(
      (packer, either) => {
        packer.packMapHeader(headerSize)
        either match {
          case Left(a) =>
            X.pack(packer, LeftKey)
            A.pack(packer, a)
          case Right(b) =>
            X.pack(packer, RightKey)
            B.pack(packer, b)
        }
        packer.mapEnd()
      }
      ,
      unpacker => {
        val size = unpacker.unpackMapHeader()
        if(size == headerSize){
          for {
            value <- X.unpack(unpacker)
            result <- value match {
              case LeftKey =>
                A.unpack(unpacker).map(Left(_))
              case RightKey =>
                B.unpack(unpacker).map(Right(_))
              case other =>
                -\/(new UnexpectedEitherKey(left = LeftKey, right = RightKey, other))
            }
          } yield {
            unpacker.mapEnd()
            result
          }
        }else{
          -\/(new UnexpectedMapSize(headerSize, size))
        }
      }
    )

}
