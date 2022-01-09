package msgpack4z

import msgpack4z.CodecInstances.all._
import scalaz._
import scalaz.std.AllInstances._

abstract class ScalazSpec extends SpecBase {

/*
  val iList = checkLawz[IList[Long]]
  val iSet = checkLawz[ISet[Long]]
  val maybe = checkLawz[Maybe[Long]]
  val iMap = checkLawz[String ==>> Maybe[Byte]]
  val nonEmptyList = checkLawz[NonEmptyList[NonEmptyList[Long]]]
*/

  import msgpack4z.EitherCodec.eitherCompactCodec

  private type EitherL = List[Short] \/ Long
  private type EitherR = Either[Int, String]
  private type EitherTest = EitherL \/ EitherR

/*
  val `disjunction String` = {
    import msgpack4z.DisjunctionCodec.disjunctionStringCodec
    checkLaw[EitherTest]
  }

  val `disjunction Compact` = {
    import msgpack4z.DisjunctionCodec.disjunctionCompactCodec
    checkLaw[EitherTest]
  }
*/
}
