package msgpack4z

import java.math.BigInteger
import scalaprops._
import scalaprops.ScalapropsScalaz._
import scalaz.syntax.functor._

object UnionGen {
  val booleanGen: Gen[MsgpackUnion] =
    Gen.elements(
      MsgpackUnion.True,
      MsgpackUnion.False
    )

  val longGen: Gen[MsgpackUnion] =
    Gen.from1(MsgpackLong)

  val ulongGen: Gen[MsgpackUnion] =
    Gen
      .chooseLong(0, Long.MaxValue)
      .flatMap(i =>
        Gen.elements(
          MsgpackULong(BigInteger.valueOf(i)),
          MsgpackULong(BigInteger.valueOf(i).add(BigInteger.valueOf(i - 1L)))
        )
      )

  val integerGen: Gen[MsgpackUnion] =
    Gen.oneOf(ulongGen, longGen)

  implicit val scalaDoubleGen: Gen[Double] =
    Gen[Long].map { n =>
      java.lang.Double.longBitsToDouble(n) match {
        case x if x.isNaN => n
        case x => x
      }
    }

  implicit val scalaFloatGen: Gen[Float] =
    Gen[Int].map { n =>
      java.lang.Float.intBitsToFloat(n) match {
        case x if x.isNaN => n
        case x => x
      }
    }

  val doubleGen: Gen[MsgpackUnion] =
    Gen.oneOf(
      Gen[Double].map(MsgpackUnion.double),
      Gen[Float].map(MsgpackUnion.float)
    )

  val numberGen: Gen[MsgpackUnion] =
    Gen.oneOf(integerGen, doubleGen)

  val stringGen: Gen[MsgpackUnion] =
    Gen.alphaNumString.map(MsgpackString)

  val binaryGen: Gen[MsgpackUnion] =
    Gen.from1(MsgpackBinary)

  val unionGen0: Gen[MsgpackUnion] =
    Gen.frequency(
      1 -> Gen.value(MsgpackUnion.nil),
      1 -> booleanGen,
      6 -> numberGen,
      6 -> stringGen,
      3 -> binaryGen
    )

  val arrayGen: Gen[MsgpackUnion] =
    Gen.list(unionGen0).map(MsgpackArray)

  val mapGen: Gen[MsgpackUnion] =
    Gen.mapGen(unionGen0, unionGen0).map(MsgpackMap)

  val extGen: Gen[MsgpackExt] =
    Gen.from2(MsgpackExt.apply)

  val unionWithoutExtGen: Gen[MsgpackUnion] =
    Gen.oneOf(
      unionGen0,
      arrayGen,
      mapGen,
      stringGen,
      binaryGen
    )

  implicit val unionGen: Gen[MsgpackUnion] =
    Gen.oneOf(
      unionGen0,
      arrayGen,
      mapGen,
      stringGen,
      extGen.widen,
      binaryGen
    )
}
