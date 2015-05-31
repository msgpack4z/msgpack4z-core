package msgpack4z

import scalaprops._
import UnionGen._

object MsgpackUnionSpec extends Scalaprops {

  val equalLaws = scalazlaws.equal.all[MsgpackUnion]

}
