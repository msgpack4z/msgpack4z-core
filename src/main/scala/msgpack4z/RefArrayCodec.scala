package msgpack4z

trait RefArrayCodec {
  implicit def refArrayCodec[A <: AnyRef: reflect.ClassTag](implicit A: MsgpackCodec[A]): MsgpackCodec[Array[A]]
}

private[msgpack4z] trait RefArrayCodecImpl extends RefArrayCodec {
  implicit override final def refArrayCodec[A <: AnyRef: reflect.ClassTag](implicit A: MsgpackCodec[A]): MsgpackCodec[Array[A]] =
    CodecInstances.array.arrayCodecNative[A]
}
