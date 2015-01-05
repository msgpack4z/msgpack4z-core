package msgpack4z

import scalaz.{-\/, \/, \/-}

object DisjunctionCodec {
  private[this] final val headerSize = 1

  def disjunctionCodec[A, B, X](LeftKey: X, RightKey: X)(implicit A: MsgpackCodec[A], B: MsgpackCodec[B], X: MsgpackCodec[X]): MsgpackCodec[A \/ B] =
    disjunctionCodecImpl[A, B, X](LeftKey, RightKey)

  implicit def disjunctionStringCodec[A: MsgpackCodec, B: MsgpackCodec]: MsgpackCodec[A \/ B] = {
    import CodecInstances.std.stringCodec
    disjunctionCodec[A, B, String]("left", "right")
  }

  implicit def disjunctionCompactCodec[A: MsgpackCodec, B: MsgpackCodec]: MsgpackCodec[A \/ B] = {
    import CodecInstances.anyVal.byteCodec
    disjunctionCodec[A, B, Byte](0, 1)
  }

  private[this] def disjunctionCodecImpl[A, B, X](LeftKey: X, RightKey: X)(implicit A: MsgpackCodec[A], B: MsgpackCodec[B], X: MsgpackCodec[X]): MsgpackCodec[A \/ B] =
    MsgpackCodec.tryE(
      (packer, disjunction) => {
        packer.packMapHeader(headerSize)
        disjunction match {
          case -\/(a) =>
            X.pack(packer, LeftKey)
            A.pack(packer, a)
          case \/-(b) =>
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
                A.unpack(unpacker).map(\/.left)
              case RightKey =>
                B.unpack(unpacker).map(\/.right)
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
