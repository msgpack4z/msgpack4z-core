package msgpack4z

import msgpack4z.CodecInstances.all._
import msgpack4z.UnionGen._
import scala.util.Random
import scalaprops._
import scalaz.std.AllInstances._

abstract class StdSpec extends SpecBase {

  val `string 16` = checkLaw[String](
    implicitly,
    Gen.value(Random.alphanumeric.take(1 << (8 + 1)).mkString)
  )

  val `string 32` = checkLaw[String](
    implicitly,
    Gen.value(Random.alphanumeric.take(1 << (16 + 1)).mkString)
  )

  val int = checkLaw[Int]
  val short = checkLaw[Short]
  val double = checkLaw[Double]
  val float = checkLaw[Float]
  val long = checkLaw[Long]
  val boolean = checkLaw[Boolean]
  val symbol = checkLaw[Symbol]
  val string = checkLaw[String]

  implicit val twentyTwoCodec: MsgpackCodec[TwentyTwo] =
    CaseCodec.codec22(TwentyTwo.apply, TwentyTwo.unapply)

  val option = checkLaw[Option[Option[Long]]]

  val `List[Int]` = checkLaw[List[Int]]
  val `Vector[Long]` = checkLaw[List[Long]]


  val tuple1 = checkLawz[Tuple1[Long]]
  val tuple2 = checkLawz[(Long, Int)]


  val `Map[String, String]` = checkLawz[Map[String, String]]

  val `Either String` = checkLaw[Either[Int, Long]](EitherCodec.eitherStringCodec, implicitly)
  val `Either Compact` = checkLaw[Either[List[Int], Long]](EitherCodec.eitherCompactCodec, implicitly)

  val twentyTwo = checkLaw[TwentyTwo]

  val tuple22 = checkLaw[(Long, String, Long, Long, Long, Int, Long, Long, Long, Long, List[Long], Long, Long, Long, Long, String, Long, Long, Long, Long, Long, Long)]

}
