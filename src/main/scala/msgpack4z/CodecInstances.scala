package msgpack4z

object CodecInstances {
  val all: AnyValCodec & TupleCodec & AnyValArrayCodec & RefArrayCodec & StdCodec & BinaryCodec & ScalazCodec = AllImpl

  val anyVal: AnyValCodec = AllImpl

  val tuple: TupleCodec = AllImpl

  val std: StdCodec = AllImpl

  val anyValArray: AnyValArrayCodec = AllImpl

  val refArray: RefArrayCodec = AllImpl

  val array: ArrayCodec = ArrayCodecImpl

  val binary: BinaryCodec = AllImpl

  val booleanArray: BooleanArrayCodec = BooleanArrayCodecImpl
  val byteArray: ByteArrayCodec = ByteArrayCodecImpl
  val shortArray: ShortArrayCodec = ShortArrayCodecImpl
  val intArray: IntArrayCodec = IntArrayCodecImpl
  val longArray: LongArrayCodec = LongArrayCodecImpl
  val floatArray: FloatArrayCodec = FloatArrayCodecImpl
  val doubleArray: DoubleArrayCodec = DoubleArrayCodecImpl
}
