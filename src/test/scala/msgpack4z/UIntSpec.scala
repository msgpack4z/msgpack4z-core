package msgpack4z

import scalaprops.Gen
import scalaprops.Property.forAllG

abstract class UIntSpec extends SpecBase {

  override def param = super.param.copy(minSuccessful = 1000)

  private[this] def chooseWithBoundaries(min: Int, max: Int) =
    Gen.frequency(
      1 -> Gen.value(min),
      1 -> Gen.value(min + 1),
      1 -> Gen.value(max),
      1 -> Gen.value(max - 1),
      1 -> Gen.value((max + min) / 2 - 1),
      1 -> Gen.value((max + min) / 2 + 1),
      1 -> Gen.value((max + min) / 2),
      90 -> Gen.choose(min, max)
    )

  private[this] def chooseLongWithBoundaries(min: Long, max: Long) =
    Gen.frequency(
      1 -> Gen.value(min),
      1 -> Gen.value(min + 1),
      1 -> Gen.value(max),
      1 -> Gen.value(max - 1),
      1 -> Gen.value((max + min) / 2 - 1),
      1 -> Gen.value((max + min) / 2 + 1),
      1 -> Gen.value((max + min) / 2),
      90 -> Gen.chooseLong(min, max)
    )

  val uint8 = forAllG(chooseWithBoundaries(0, (1 << 8) - 1)){ x =>
    def u = unpacker(Array[Byte](msgpack4z.Code.UINT8, x.toByte))
    if (x < 128) {
      assert(u.unpackByte() == x)
    } else {
      expectException {
        u.unpackByte()
      }
    }
    assert(u.unpackShort() == x)
    assert(u.unpackInt() == x)
    assert(u.unpackLong() == x)
    assert(u.unpackBigInteger().bitLength() <= java.lang.Byte.SIZE)
    assert(u.unpackBigInteger().shortValue() == x)
    true
  }

  val uint16 = forAllG(chooseWithBoundaries(0, (1 << 16) - 1)){ x =>
    def u = {
      val buf = MsgOutBuffer.create()
      buf.writeByteAndShort(Code.UINT16, x.toShort)
      unpacker(buf.result)
    }
    if (x <= Byte.MaxValue) {
      assert(u.unpackByte() == x)
    } else {
      expectException {
        u.unpackByte()
      }
    }
    if (x <= Short.MaxValue) {
      assert(u.unpackShort() == x)
    } else {
      expectException {
        u.unpackShort()
      }
    }
    assert(u.unpackInt() == x)
    assert(u.unpackLong() == x)
    assert(u.unpackBigInteger().bitLength() <= java.lang.Short.SIZE)
    assert(u.unpackBigInteger().intValue() == x)
    true
  }

  val uint32 = {
    val g = Gen.frequency(
      1 -> Gen.value(Int.MaxValue.toLong - 1),
      1 -> Gen.value(Int.MaxValue.toLong),
      1 -> Gen.value(Int.MaxValue.toLong + 1),
      1 -> Gen.value(Int.MaxValue.toLong * 2 - 1),
      90 -> chooseLongWithBoundaries(0, Int.MaxValue.toLong * 2 + 1)
    )

    forAllG(g) { x =>
      def u = {
        val buf = MsgOutBuffer.create()
        buf.writeByteAndInt(Code.UINT32, x.intValue())
        unpacker(buf.result)
      }
      if (x <= Byte.MaxValue) {
        assert(u.unpackByte() == x, "byte")
      } else {
        expectException {
          u.unpackByte()
        }
      }
      if (x <= Short.MaxValue) {
        assert(u.unpackShort() == x, "short")
      } else {
        expectException {
          u.unpackShort()
        }
      }
      if (x <= Int.MaxValue) {
        assert(u.unpackInt() == x, "int")
      } else {
        expectException {
          u.unpackInt()
        }
      }
      assert(u.unpackLong() == x, "long")
      assert(u.unpackBigInteger().bitLength() <= java.lang.Integer.SIZE)
      assert(u.unpackBigInteger().longValue() == x, "biginteger")
      true
    }
  }


  val uint64 = {
    val g = {
      val longPos = chooseLongWithBoundaries(0, Long.MaxValue)

      Gen.frequency(
        1 -> Gen.value(BigInt(Long.MaxValue) + 1),
        1 -> Gen.value(BigInt(Long.MaxValue) * 2 - 1),
        1 -> Gen.value(BigInt(Long.MaxValue) * 2),
        1 -> Gen.value(BigInt(Long.MaxValue) * 2 + 1),
        10 -> longPos.map(BigInt(_)),
        80 -> longPos.flatMap(a1 => longPos.map(BigInt(a1) + _ + 1))
      )
    }

    forAllG(g){ x =>
      def u = {
        val buf = MsgOutBuffer.create()
        buf.writeByteAndLong(Code.UINT64, x.longValue())
        unpacker(buf.result)
      }
      if (x <= Byte.MaxValue) {
        assert(u.unpackByte() == x, "byte")
      } else {
        expectException {
          u.unpackByte
        }
      }
      if (x <= Short.MaxValue) {
        assert(u.unpackShort() == x, "short")
      } else {
        expectException {
          u.unpackShort
        }
      }
      if (x <= Int.MaxValue) {
        assert(u.unpackInt() == x, "int")
      } else {
        expectException {
          u.unpackInt()
        }
      }
      if (x <= Long.MaxValue) {
        assert(u.unpackLong() == x, "long")
      } else {
        expectException {
          u.unpackLong()
        }
      }
      assert(u.unpackBigInteger().bitLength() <= java.lang.Long.SIZE)
      assert(u.unpackBigInteger() == x.bigInteger, "biginteger")
      true
    }
  }

}
