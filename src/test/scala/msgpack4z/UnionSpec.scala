package msgpack4z

import org.scalacheck.{Arbitrary, Prop}
import scalaz._

abstract class UnionSpec(name: String) extends SpecBase(name + " union") {

  private implicit val unionArb =
    Arbitrary(UnionArbitrary.unionGen)

  property("union") = Prop.secure{
    checkLaw(MsgpackUnion.codecInstance, unionArb)
  }

  property("union equals hashCode") = Prop.forAll{ a: MsgpackUnion =>
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

  property("MsgpackLong and MsgpackULong") = Prop.forAll{ a: Long =>
    val x = MsgpackLong(a)
    val y = MsgpackULong(new java.math.BigInteger(a.toString))
    (y == x) && (x == y) && (x.hashCode == y.hashCode)
  }

}
