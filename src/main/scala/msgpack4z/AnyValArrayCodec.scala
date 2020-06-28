package msgpack4z

// GENERATED CODE: DO NOT EDIT.
trait AnyValArrayCodec {
  implicit def arrayBoolean: MsgpackCodec[Array[Boolean]]
  def arrayByte: MsgpackCodec[Array[Byte]]
  implicit def arrayShort: MsgpackCodec[Array[Short]]
  implicit def arrayInt: MsgpackCodec[Array[Int]]
  implicit def arrayLong: MsgpackCodec[Array[Long]]
  implicit def arrayFloat: MsgpackCodec[Array[Float]]
  implicit def arrayDouble: MsgpackCodec[Array[Double]]
}

private[msgpack4z] trait AnyValArrayCodecImpl extends AnyValArrayCodec {


  implicit final override val arrayBoolean: MsgpackCodec[Array[Boolean]] = MsgpackCodec.tryConst(
    { (packer, array) =>
      packer.packArrayHeader(array.length)
      var i = 0
      while (i < array.length) {
        packer.packBoolean(array(i))
        i += 1
      }
      packer.arrayEnd()
    },
    { unpacker =>
      val size = unpacker.unpackArrayHeader()
      val array = new Array[Boolean](size)
      var i = 0
      while(i < size){
        array(i) = unpacker.unpackBoolean()
        i += 1
      }
      unpacker.arrayEnd()
      array
    }
  )


  implicit final override val arrayByte: MsgpackCodec[Array[Byte]] = MsgpackCodec.tryConst(
    { (packer, array) =>
      packer.packArrayHeader(array.length)
      var i = 0
      while (i < array.length) {
        packer.packByte(array(i))
        i += 1
      }
      packer.arrayEnd()
    },
    { unpacker =>
      val size = unpacker.unpackArrayHeader()
      val array = new Array[Byte](size)
      var i = 0
      while(i < size){
        array(i) = unpacker.unpackByte()
        i += 1
      }
      unpacker.arrayEnd()
      array
    }
  )


  implicit final override val arrayShort: MsgpackCodec[Array[Short]] = MsgpackCodec.tryConst(
    { (packer, array) =>
      packer.packArrayHeader(array.length)
      var i = 0
      while (i < array.length) {
        packer.packShort(array(i))
        i += 1
      }
      packer.arrayEnd()
    },
    { unpacker =>
      val size = unpacker.unpackArrayHeader()
      val array = new Array[Short](size)
      var i = 0
      while(i < size){
        array(i) = unpacker.unpackShort()
        i += 1
      }
      unpacker.arrayEnd()
      array
    }
  )


  implicit final override val arrayInt: MsgpackCodec[Array[Int]] = MsgpackCodec.tryConst(
    { (packer, array) =>
      packer.packArrayHeader(array.length)
      var i = 0
      while (i < array.length) {
        packer.packInt(array(i))
        i += 1
      }
      packer.arrayEnd()
    },
    { unpacker =>
      val size = unpacker.unpackArrayHeader()
      val array = new Array[Int](size)
      var i = 0
      while(i < size){
        array(i) = unpacker.unpackInt()
        i += 1
      }
      unpacker.arrayEnd()
      array
    }
  )


  implicit final override val arrayLong: MsgpackCodec[Array[Long]] = MsgpackCodec.tryConst(
    { (packer, array) =>
      packer.packArrayHeader(array.length)
      var i = 0
      while (i < array.length) {
        packer.packLong(array(i))
        i += 1
      }
      packer.arrayEnd()
    },
    { unpacker =>
      val size = unpacker.unpackArrayHeader()
      val array = new Array[Long](size)
      var i = 0
      while(i < size){
        array(i) = unpacker.unpackLong()
        i += 1
      }
      unpacker.arrayEnd()
      array
    }
  )


  implicit final override val arrayFloat: MsgpackCodec[Array[Float]] = MsgpackCodec.tryConst(
    { (packer, array) =>
      packer.packArrayHeader(array.length)
      var i = 0
      while (i < array.length) {
        packer.packFloat(array(i))
        i += 1
      }
      packer.arrayEnd()
    },
    { unpacker =>
      val size = unpacker.unpackArrayHeader()
      val array = new Array[Float](size)
      var i = 0
      while(i < size){
        array(i) = unpacker.unpackFloat()
        i += 1
      }
      unpacker.arrayEnd()
      array
    }
  )


  implicit final override val arrayDouble: MsgpackCodec[Array[Double]] = MsgpackCodec.tryConst(
    { (packer, array) =>
      packer.packArrayHeader(array.length)
      var i = 0
      while (i < array.length) {
        packer.packDouble(array(i))
        i += 1
      }
      packer.arrayEnd()
    },
    { unpacker =>
      val size = unpacker.unpackArrayHeader()
      val array = new Array[Double](size)
      var i = 0
      while(i < size){
        array(i) = unpacker.unpackDouble()
        i += 1
      }
      unpacker.arrayEnd()
      array
    }
  )


}


trait BooleanArrayCodec {
  implicit def arrayBoolean: MsgpackCodec[Array[Boolean]]
}

private[msgpack4z] object BooleanArrayCodecImpl extends BooleanArrayCodec {
  implicit override val arrayBoolean: MsgpackCodec[Array[Boolean]] = CodecInstances.all.arrayBoolean
}


trait ByteArrayCodec {
  implicit def arrayByte: MsgpackCodec[Array[Byte]]
}

private[msgpack4z] object ByteArrayCodecImpl extends ByteArrayCodec {
  implicit override val arrayByte: MsgpackCodec[Array[Byte]] = CodecInstances.all.arrayByte
}


trait ShortArrayCodec {
  implicit def arrayShort: MsgpackCodec[Array[Short]]
}

private[msgpack4z] object ShortArrayCodecImpl extends ShortArrayCodec {
  implicit override val arrayShort: MsgpackCodec[Array[Short]] = CodecInstances.all.arrayShort
}


trait IntArrayCodec {
  implicit def arrayInt: MsgpackCodec[Array[Int]]
}

private[msgpack4z] object IntArrayCodecImpl extends IntArrayCodec {
  implicit override val arrayInt: MsgpackCodec[Array[Int]] = CodecInstances.all.arrayInt
}


trait LongArrayCodec {
  implicit def arrayLong: MsgpackCodec[Array[Long]]
}

private[msgpack4z] object LongArrayCodecImpl extends LongArrayCodec {
  implicit override val arrayLong: MsgpackCodec[Array[Long]] = CodecInstances.all.arrayLong
}


trait FloatArrayCodec {
  implicit def arrayFloat: MsgpackCodec[Array[Float]]
}

private[msgpack4z] object FloatArrayCodecImpl extends FloatArrayCodec {
  implicit override val arrayFloat: MsgpackCodec[Array[Float]] = CodecInstances.all.arrayFloat
}


trait DoubleArrayCodec {
  implicit def arrayDouble: MsgpackCodec[Array[Double]]
}

private[msgpack4z] object DoubleArrayCodecImpl extends DoubleArrayCodec {
  implicit override val arrayDouble: MsgpackCodec[Array[Double]] = CodecInstances.all.arrayDouble
}
