package msgpack4z

import org.scalacheck.{Gen, Prop}

abstract class UIntSpec(name: String) extends SpecBase(name + " unsigned int") {

  property("uint8") = Prop.secure{
    Prop.forAll(Gen.choose(0, (1 << 8) - 1)){ x =>
      def u = unpacker(Array[Byte](msgpack4z.Code.UINT8, x.toByte))
      if (x < 128) {
        assert(u.unpackByte() == x)
      }
      assert(u.unpackShort() == x)
      assert(u.unpackInt() == x)
      assert(u.unpackLong() == x)
      assert(u.unpackBigInteger().bitLength() <= java.lang.Byte.SIZE)
      assert(u.unpackBigInteger().shortValue() == x)
      true
    }
  }

  property("uint16") = Prop.secure{
    Prop.forAll(Gen.choose(0, (1 << 16) - 1)){ x =>
      def u = {
        val buf = MsgOutBuffer.create()
        buf.writeByteAndShort(Code.UINT16, x.toShort)
        unpacker(buf.result)
      }
      if (x < Byte.MaxValue) {
        assert(u.unpackByte() == x)
      }
      if (x < Short.MaxValue) {
        assert(u.unpackShort() == x)
      }
      assert(u.unpackInt() == x)
      assert(u.unpackLong() == x)
      assert(u.unpackBigInteger().bitLength() <= java.lang.Short.SIZE)
      assert(u.unpackBigInteger().intValue() == x)
      true
    }
  }

  property("uint32") = Prop.secure{
    Prop.forAll(Gen.choose(0L, (1L << 32) - 1)){ x =>
      def u = {
        val buf = MsgOutBuffer.create()
        buf.writeByteAndInt(Code.UINT32, x.intValue())
        unpacker(buf.result)
      }
      if (x < Byte.MaxValue) {
        assert(u.unpackByte() == x, "byte")
      }
      if (x < Short.MaxValue) {
        assert(u.unpackShort() == x, "short")
      }
      if (x < Int.MaxValue) {
        assert(u.unpackInt() == x, "int")
      }
      assert(u.unpackLong() == x, "long")
      assert(u.unpackBigInteger().bitLength() <= java.lang.Integer.SIZE)
      assert(u.unpackBigInteger().longValue() == x, "biginteger")
      true
    }
  }

  property("uint64") = Prop.secure{
    val g = Gen.posNum[Long].flatMap(a1 => Gen.posNum[Long].map(BigInt(a1) + _))
    Prop.forAll(g){ x =>
      def u = {
        val buf = MsgOutBuffer.create()
        buf.writeByteAndLong(Code.UINT64, x.longValue())
        unpacker(buf.result)
      }
      if (x < Byte.MaxValue) {
        assert(u.unpackByte() == x, "byte")
      }
      if (x < Short.MaxValue) {
        assert(u.unpackShort() == x, "short")
      }
      if (x < Int.MaxValue) {
        assert(u.unpackInt() == x, "int")
      }
      if (x < Long.MaxValue) {
        assert(u.unpackLong() == x, "long")
      }
      assert(u.unpackBigInteger().bitLength() <= java.lang.Long.SIZE)
      assert(u.unpackBigInteger() == x.bigInteger, "biginteger")
      true
    }
  }

}
