package msgpack4z

import java.math.BigInteger
import org.scalacheck.{Arbitrary, Gen}

object UnionArbitrary {

  val booleanGen: Gen[MsgpackUnion] =
    Gen.oneOf(
      Gen.const(MsgpackUnion.True),
      Gen.const(MsgpackUnion.False)
    )

  val longGen: Gen[MsgpackUnion] =
    implicitly[Arbitrary[Long]].arbitrary.map(MsgpackLong)

  val ulongGen: Gen[MsgpackUnion] =
    Gen.choose(0L, Long.MaxValue).flatMap(i => Gen.oneOf(
      MsgpackULong(new BigInteger(i.toString)),
      MsgpackULong(new BigInteger(i.toString).add(new BigInteger((i - 1).toString)))
    ))

  val integerGen: Gen[MsgpackUnion] =
    Gen.oneOf(ulongGen, longGen)

  val doubleGen: Gen[MsgpackUnion] =
    Gen.oneOf(
      implicitly[Arbitrary[Float]].arbitrary.map(MsgpackUnion.float),
      implicitly[Arbitrary[Double]].arbitrary.map(MsgpackDouble)
    )

  val numberGen: Gen[MsgpackUnion] =
    Gen.oneOf(integerGen, doubleGen)

  val stringGen: Gen[MsgpackUnion] =
    implicitly[Arbitrary[String]].arbitrary.map(MsgpackString)

  val binaryGen: Gen[MsgpackUnion] =
    implicitly[Arbitrary[Array[Byte]]].arbitrary.map(MsgpackBinary)

  val unionGen0: Gen[MsgpackUnion] =
    Gen.frequency(
      1 -> Gen.const(MsgpackUnion.nil),
      1 -> booleanGen,
      6 -> numberGen,
      6 -> stringGen,
      3 -> binaryGen
    )

  val arrayGen: Gen[MsgpackUnion] =
    Gen.listOf(unionGen0).map(MsgpackArray)

  val mapGen: Gen[MsgpackUnion] =
    Gen.containerOf[Map, MsgpackUnion, MsgpackUnion](Gen.zip(unionGen0, unionGen0)).map(MsgpackMap)

  val unionGen: Gen[MsgpackUnion] =
    Gen.oneOf(
      unionGen0,
      arrayGen,
      mapGen,
      stringGen,
      binaryGen
    )

}
