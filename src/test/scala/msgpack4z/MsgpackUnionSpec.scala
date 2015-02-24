package msgpack4z

import org.scalacheck.{Arbitrary, Properties}
import scalaz.scalacheck.ScalazProperties

object MsgpackUnionSpec extends Properties("MsgpackUnion") {

  private implicit val unionArb =
    Arbitrary(UnionArbitrary.unionGen)

  property("union equal instance") =
    ScalazProperties.equal.laws[MsgpackUnion]

}
