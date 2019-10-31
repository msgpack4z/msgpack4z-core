package msgpack4z

import scala.reflect.ClassTag
import scalaz.{-\/, \/-}

trait ArrayCodec {
  implicit def arrayCodec[A](implicit A: MsgpackCodec[A], C: ClassTag[A]): MsgpackCodec[Array[A]]
  def arrayCodecNative[A](implicit A: MsgpackCodec[A], C: ClassTag[A]): MsgpackCodec[Array[A]]
}

object ArrayCodecImpl extends ArrayCodec {
  implicit def arrayCodec[A](implicit A: MsgpackCodec[A], C: ClassTag[A]): MsgpackCodec[Array[A]] =
    (C match {
      case ClassTag.Byte =>
        CodecInstances.anyValArray.arrayByte
      case ClassTag.Short =>
        CodecInstances.anyValArray.arrayShort
      case ClassTag.Int =>
        CodecInstances.anyValArray.arrayInt
      case ClassTag.Long =>
        CodecInstances.anyValArray.arrayLong
      case ClassTag.Float =>
        CodecInstances.anyValArray.arrayFloat
      case ClassTag.Double =>
        CodecInstances.anyValArray.arrayDouble
      case ClassTag.Boolean =>
        CodecInstances.anyValArray.arrayBoolean
      case _ =>
        arrayCodecNative[A]
    }).asInstanceOf[MsgpackCodec[Array[A]]]

  override final def arrayCodecNative[A](implicit A: MsgpackCodec[A], C: ClassTag[A]): MsgpackCodec[Array[A]] = MsgpackCodec.tryE(
    (packer, array) => {
      packer.packArrayHeader(array.length)
      var i = 0
      while (i < array.length) {
        A.pack(packer, array(i))
        i += 1
      }
      packer.arrayEnd()
    },
    unpacker => {
      val size = unpacker.unpackArrayHeader()
      val array = new Array[A](size)
      var i = 0
      var error: -\/[UnpackError] = null
      while (i < size && error == null) {
        A.unpack(unpacker) match {
          case \/-(a) =>
            array(i) = a
          case e @ -\/(_) =>
            error = e
        }
        i += 1
      }
      unpacker.arrayEnd()
      if (error == null)
        \/-(array)
      else
        error
    }
  )
}
