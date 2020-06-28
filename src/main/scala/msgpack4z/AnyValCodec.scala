package msgpack4z

// GENERATED CODE: DO NOT EDIT.
trait AnyValCodec {
  implicit def booleanCodec: MsgpackCodec[Boolean]
  implicit def byteCodec: MsgpackCodec[Byte]
  implicit def shortCodec: MsgpackCodec[Short]
  implicit def intCodec: MsgpackCodec[Int]
  implicit def longCodec: MsgpackCodec[Long]
  implicit def floatCodec: MsgpackCodec[Float]
  implicit def doubleCodec: MsgpackCodec[Double]
}

private[msgpack4z] trait AnyValCodecImpl extends AnyValCodec {

  override final def booleanCodec: MsgpackCodec[Boolean] =
    MsgpackCodec.tryConst(_ packBoolean _, _.unpackBoolean())

  override final def byteCodec: MsgpackCodec[Byte] =
    MsgpackCodec.tryConst(_ packByte _, _.unpackByte())

  override final def shortCodec: MsgpackCodec[Short] =
    MsgpackCodec.tryConst(_ packShort _, _.unpackShort())

  override final def intCodec: MsgpackCodec[Int] =
    MsgpackCodec.tryConst(_ packInt _, _.unpackInt())

  override final def longCodec: MsgpackCodec[Long] =
    MsgpackCodec.tryConst(_ packLong _, _.unpackLong())

  override final def floatCodec: MsgpackCodec[Float] =
    MsgpackCodec.tryConst(_ packFloat _, _.unpackFloat())

  override final def doubleCodec: MsgpackCodec[Double] =
    MsgpackCodec.tryConst(_ packDouble _, _.unpackDouble())

}
