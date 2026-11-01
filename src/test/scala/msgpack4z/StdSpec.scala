package msgpack4z

import msgpack4z.AsTuple._
import msgpack4z.CodecInstances.all._
import msgpack4z.UnionGen._
import scala.util.Random
import scalaprops._
import scalaz.std.AllInstances._

abstract class StdSpec extends SpecBase {
  private[this] def isSurrogate(c: Char): Boolean =
    (Character.MIN_SURROGATE <= c) && (c <= Character.MAX_SURROGATE)

  property("unicode") = checkLaw[String](using
    implicitly,
    Gen.arrayOfN(100, Gen.genCharAll).map { s =>
      val b = new java.lang.StringBuilder
      var i = 0
      while (i < s.length) {
        val c = s(i)
        if (!isSurrogate(c)) {
          b.append(c)
          i += 1
        } else if ((i + 1 < s.length) && Character.isSurrogatePair(c, s(i + 1))) {
          b.append(c)
          b.append(s(i + 1))
          i += 2
        } else {
          i += 1
        }
      }
      b.toString
    }
  )

  property("string 16") = checkLaw[String](using
    implicitly,
    Gen.value(Random.alphanumeric.take(1 << (8 + 1)).mkString)
  )

  property("string 32") = checkLaw[String](using
    implicitly,
    Gen.value(Random.alphanumeric.take((1 << 16) + 5).mkString)
  )

  property("int") = checkLaw[Int]
  property("short") = checkLaw[Short]
  property("double") = checkLaw[Double]
  property("float") = checkLaw[Float]
  property("long") = checkLaw[Long]
  property("boolean") = checkLaw[Boolean]
  property("symbol") = checkLaw[Symbol]
  property("string") = checkLaw[String]

  implicit val twentyTwoCodec: MsgpackCodec[TwentyTwo] =
    CaseCodec.codec22(TwentyTwo.apply, (x: TwentyTwo) => Some(x.asTuple))

  property("option") = checkLaw[Option[Option[Long]]]

  property("List[Int]") = checkLaw[List[Int]]
  property("Vector[Long]") = checkLaw[List[Long]]

  property("tuple1") = checkLawz[Tuple1[Long]]
  property("tuple2") = checkLawz[(Long, Int)]

  property("Map[String, String]") = checkLawz[Map[String, String]]

  property("Either String") = checkLaw[Either[Int, Long]](using EitherCodec.eitherStringCodec, implicitly)
  property("Either Compact") = checkLaw[Either[List[Int], Long]](using EitherCodec.eitherCompactCodec, implicitly)

  property("twentyTwo") = checkLaw[TwentyTwo]

  property("tuple22") = checkLaw[
    (Long, String, Long, Long, Long, Int, Long, Long, Long, Long, List[Long], Long, Long, Long, Long, String, Long, Long, Long, Long, Long, Long)
  ]
}
