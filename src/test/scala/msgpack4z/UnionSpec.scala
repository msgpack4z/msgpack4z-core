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

  val `map imap` = Property.forAll{ a: Map[MsgpackUnion, MsgpackUnion] =>
    val b = MsgpackUnion.map(a)
    val c = IMap.fromList(a.toList)
    val d = MsgpackUnion.imap(c)
    assert(b.map == Opt(a))
    assert(b.imap == Opt(c))
    assert(b.map.map(x => IMap.fromList(x.toList)) == Opt(c))
    assert(d.map == Opt(a))
    assert(d.imap == Opt(c))
    assert(b.map.map(x => IMap.fromList(x.toList)) == Opt(c))
    true
  }
}
