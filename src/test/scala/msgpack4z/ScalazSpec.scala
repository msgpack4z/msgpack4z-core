package msgpack4z

import msgpack4z.CodecInstances.all._
import scalaz._
import scalaz.std.AllInstances._
import scalaprops.ScalapropsScalaz._

abstract class ScalazSpec extends SpecBase {

  property("iList") = checkLawz[IList[Long]]
  property("iSet") = checkLawz[ISet[Long]]
  property("maybe") = checkLawz[Maybe[Long]]
  property("iMap") = checkLawz[String ==>> Maybe[Byte]]
  property("nonEmptyList") = checkLawz[NonEmptyList[NonEmptyList[Long]]]

  import msgpack4z.EitherCodec.eitherCompactCodec

  private type EitherL = List[Short] \/ Long
  private type EitherR = Either[Int, String]
  private type EitherTest = EitherL \/ EitherR

  property("disjunction String") = {
    import msgpack4z.DisjunctionCodec.disjunctionStringCodec
    checkLaw[EitherTest]
  }

  property("disjunction Compact") = {
    import msgpack4z.DisjunctionCodec.disjunctionCompactCodec
    checkLaw[EitherTest]
  }
}
