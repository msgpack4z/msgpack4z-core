package msgpack4z

import scalaprops.Property.forAll

abstract class IntSpec extends SpecBase {

  override def param = super.param.copy(minSuccessful = 1000)

  val int8 = forAll{ x: Byte =>
    def u = unpacker(Array[Byte](msgpack4z.Code.INT8, x))
    assert(u.unpackByte() == x)
    assert(u.unpackShort() == x)
    assert(u.unpackInt() == x)
    assert(u.unpackLong() == x)
    assert(u.unpackBigInteger().bitLength() < java.lang.Byte.SIZE)
    assert(u.unpackBigInteger().byteValue == x)
    true
  }

  val int16 = forAll{ x: Short =>
    def u = {
      val buf = MsgOutBuffer.create()
      buf.writeByteAndShort(Code.INT16, x)
      unpacker(buf.result)
    }
    if(Byte.MinValue <= x && x <= Byte.MaxValue) {
      assert(u.unpackByte() == x)
    } else {
      expectException {
        u.unpackByte()
      }
    }
    assert(u.unpackShort() == x)
    assert(u.unpackInt() == x)
    assert(u.unpackLong() == x)
    assert(u.unpackBigInteger().bitLength() < java.lang.Short.SIZE)
    assert(u.unpackBigInteger().shortValue == x)
    true
  }

  val int32 = forAll{ x: Int =>
    def u = {
      val buf = MsgOutBuffer.create()
      buf.writeByteAndInt(Code.INT32, x)
      unpacker(buf.result)
    }
    if(Byte.MinValue <= x && x <= Byte.MaxValue) {
      assert(u.unpackByte() == x, "byte")
    } else {
      expectException {
        u.unpackByte()
      }
    }
    if(Short.MinValue <= x && x <= Short.MaxValue) {
      assert(u.unpackShort() == x, "short")
    } else {
      expectException {
        u.unpackShort()
      }
    }
    assert(u.unpackInt() == x, "int")
    assert(u.unpackLong() == x, "long")
    assert(u.unpackBigInteger().bitLength() < java.lang.Integer.SIZE)
    assert(u.unpackBigInteger().intValue == x, "biginteger")
    true
  }

  val int64 = forAll{ x: Long =>
    def u = {
      val buf = MsgOutBuffer.create()
      buf.writeByteAndLong(Code.INT64, x)
      unpacker(buf.result)
    }
    if(Byte.MinValue <= x && x <= Byte.MaxValue) {
      assert(u.unpackByte() == x, "byte")
    } else {
      expectException {
        u.unpackByte()
      }
    }
    if(Short.MinValue <= x && x <= Short.MaxValue) {
      assert(u.unpackShort() == x, "short")
    } else {
      expectException {
        u.unpackShort()
      }
    }
    if(Int.MinValue <= x && x <= Int.MaxValue) {
      assert(u.unpackInt() == x, "int")
    } else {
      expectException {
        u.unpackInt()
      }
    }
    assert(u.unpackLong() == x, "long")
    assert(u.unpackBigInteger().bitLength() < java.lang.Long.SIZE)
    assert(u.unpackBigInteger().longValue == x, "biginteger")
    true
  }

}
