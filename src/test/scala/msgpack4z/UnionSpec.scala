package msgpack4z

import scalaprops._
import scalaz._

abstract class UnionSpec extends SpecBase {

  private implicit val unionGen =
    UnionGen.unionGen

  val union = checkLaw(MsgpackUnion.codecInstance, unionGen)

  val `equals hashCode` = Property.forAll{ a: MsgpackUnion =>
    val M = MsgpackCodec[MsgpackUnion]
    val bytes = M.toBytes(a, packer())
    M.unpackAndClose(unpacker(bytes)) match {
      case \/-(b) =>
        (a == b) && (a.hashCode == b.hashCode)
      case -\/(e) =>
        println(e)
        false
    }
  }

  val `MsgpackLong and MsgpackULong` = Property.forAll{ a: Long =>
    val x = MsgpackLong(a)
    val y = MsgpackULong(new java.math.BigInteger(a.toString))
    (y == x) && (x == y) && (x.hashCode == y.hashCode)
  }

}
