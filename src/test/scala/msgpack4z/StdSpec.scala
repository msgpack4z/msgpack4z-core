package msgpack4z

import org.scalacheck.{Gen, Arbitrary}
import shapeless.contrib.scalacheck._
import shapeless.contrib.scalaz._
import scala.util.Random
import scalaz.scalacheck.ScalazArbitrary._
import scalaz.std.AllInstances._
import msgpack4z.CodecInstances.all._

abstract class StdSpec(name: String) extends SpecBase(name + " std") {

  property("string 16") = checkLaw[String](
    implicitly,
    Arbitrary(Gen.const(Random.alphanumeric.take(1 << (8 + 1)).mkString))
  )

  property("string 32") = checkLaw[String](
    implicitly,
    Arbitrary(Gen.const(Random.alphanumeric.take(1 << (16 + 1)).mkString))
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
    CaseCodec.codec22(TwentyTwo.apply, TwentyTwo.unapply)

  property("Option") = checkLaw[Option[Option[Long]]]

  property("List[Int]") = checkLaw[List[Int]]
  property("Vector[Long]") = checkLaw[List[Long]]


  property("Tuple1") = checkLawz[Tuple1[Long]]
  property("Tuple2") = checkLawz[(Long, Int)]


  property("Map[String, String]") = checkLawz[Map[String, String]]

  property("Either String") = checkLaw[Either[Int, Long]](EitherCodec.eitherStringCodec, implicitly)
  property("Either Compact") = checkLaw[Either[List[Int], Long]](EitherCodec.eitherCompactCodec, implicitly)

  property("TwentyTwo") = checkLaw[TwentyTwo]

  property("Tuple22") = checkLawz[(Long, String, Long, Long, Long, Int, Long, Long, Long, Long, List[Long], Long, Long, Long, Long, String, Long, Long, Long, Long, Long, Long)]

}
