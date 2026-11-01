package msgpack4z

import scalaprops._
import UnionGen._

object MsgpackUnionSpec extends Scalaprops {
  override def param =
    super.param.copy(
      minSuccessful = 10000
    )

  property("orderLaws") = scalazlaws.order.all[MsgpackUnion]
}
