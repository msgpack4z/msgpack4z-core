package msgpack4z

import msgpack4z.CodecInstances.all._
import org.scalacheck.Arbitrary
import scalaz._
import scalaz.scalacheck.ScalazArbitrary._
import scalaz.std.AllInstances._

abstract class ScalazSpec(name: String) extends SpecBase(name + " scalaz"){

  implicit final val alpha = Arbitrary(org.scalacheck.Gen.alphaStr)

  property("IList") = checkLawz[IList[Long]]
  property("Maybe") = checkLawz[Maybe[Long]]
  property("==>>") = checkLawWithoutHashCode[String ==>> Maybe[Byte]]
  property("NonEmptyList") = checkLawz[NonEmptyList[NonEmptyList[Long]]]

  import msgpack4z.EitherCodec.eitherCompactCodec

  private type EitherL = List[Short] \/ Long
  private type EitherR = Either[Int, String]
  private type EitherTest = EitherL \/ EitherR

  property("""\/ String""") = {
    import msgpack4z.DisjunctionCodec.disjunctionStringCodec
    checkLaw[EitherTest]
  }
  property("""\/ Compact""") = {
    import msgpack4z.DisjunctionCodec.disjunctionCompactCodec
    checkLaw[EitherTest]
  }
}
