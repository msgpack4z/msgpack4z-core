package msgpack4z

import org.scalacheck.Prop

abstract class IntSpec(name: String) extends SpecBase(name + " int") {

  property("int8") = Prop.secure{
    Prop.forAll{ x: Byte =>
      def u = unpacker(Array[Byte](msgpack4z.Code.INT8, x))
      assert(u.unpackByte() == x)
      assert(u.unpackShort() == x)
      assert(u.unpackInt() == x)
      assert(u.unpackLong() == x)
      assert(u.unpackBigInteger().bitLength() < java.lang.Byte.SIZE)
      assert(u.unpackBigInteger().byteValue == x)
      true
    }
  }

  property("int16") = Prop.secure{
    Prop.forAll{ x: Short =>
      def u = {
        val buf = MsgOutBuffer.create()
        buf.writeByteAndShort(Code.INT16, x)
        unpacker(buf.result)
      }
      if(Byte.MinValue <= x && x <= Byte.MaxValue) {
        assert(u.unpackByte() == x)
      }
      assert(u.unpackShort() == x)
      assert(u.unpackInt() == x)
      assert(u.unpackLong() == x)
      assert(u.unpackBigInteger().bitLength() < java.lang.Short.SIZE)
      assert(u.unpackBigInteger().shortValue == x)
      true
    }
  }

  property("int32") = Prop.secure{
    Prop.forAll{ x: Int =>
      def u = {
        val buf = MsgOutBuffer.create()
        buf.writeByteAndInt(Code.INT32, x)
        unpacker(buf.result)
      }
      if(Byte.MinValue <= x && x <= Byte.MaxValue) {
        assert(u.unpackByte() == x, "byte")
      }
      if(Short.MinValue <= x && x <= Short.MaxValue) {
        assert(u.unpackShort() == x, "short")
      }
      assert(u.unpackInt() == x, "int")
      assert(u.unpackLong() == x, "long")
      assert(u.unpackBigInteger().bitLength() < java.lang.Integer.SIZE)
      assert(u.unpackBigInteger().intValue == x, "biginteger")
      true
    }
  }

  property("int64") = Prop.secure{
    Prop.forAll{ x: Long =>
      def u = {
        val buf = MsgOutBuffer.create()
        buf.writeByteAndLong(Code.INT64, x)
        unpacker(buf.result)
      }
      if(Byte.MinValue <= x && x <= Byte.MaxValue) {
        assert(u.unpackByte() == x, "byte")
      }
      if(Short.MinValue <= x && x <= Short.MaxValue) {
        assert(u.unpackShort() == x, "short")
      }
      if(Int.MinValue <= x && x <= Int.MaxValue) {
        assert(u.unpackInt() == x, "int")
      }
      assert(u.unpackLong() == x, "long")
      assert(u.unpackBigInteger().bitLength() < java.lang.Long.SIZE)
      assert(u.unpackBigInteger().longValue == x, "biginteger")
      true
    }
  }

}
