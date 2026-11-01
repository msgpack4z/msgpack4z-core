package msgpack4z

import scala.util.Random
import scalaprops._
import scalaz._

abstract class UnionSpec(unionGen0: Gen[MsgpackUnion] = UnionGen.unionGen) extends SpecBase {
  private implicit def unionGen: Gen[MsgpackUnion] = unionGen0

  private def supportExtType: Boolean = unionGen == UnionGen.unionGen

  property("union") = checkLaw(using MsgpackUnion.codecInstance, unionGen)

  property("equals hashCode") = Property.forAll { (a: MsgpackUnion) =>
    val M = MsgpackCodec[MsgpackUnion]
    val bytes = M.toBytes(a, packer())
    M.unpackAndClose(unpacker(bytes)) match {
      case \/-(b) =>
        (a == b) && (a.hashCode == b.hashCode)
      case -\/(e) =>
        println(e)
        false
    }
  }.toProperties((), Param.minSuccessful(10000))

  property("MsgpackLong and MsgpackULong") = Property.forAll { (a: Long) =>
    val x = MsgpackLong(a)
    val y = MsgpackULong(java.math.BigInteger.valueOf(a))
    (y == x) && (x == y) && (x.hashCode == y.hashCode)
  }

  property("MsgpackLong pack/unpack MsgpackLong") = Property.forAll { (a: Long) =>
    val M = MsgpackCodec[MsgpackUnion]
    val b = MsgpackLong(a)
    val c = M.toBytes(b, packer())
    M.unpackAndClose(unpacker(c)) match {
      case \/-(_: MsgpackLong) =>
        true
      case other =>
        sys.error(other.toString)
    }
  }

  property("extEqualsHashcode") = Property.forAllG(UnionGen.extGen) { (e1: MsgpackExt) =>
    val e2 = e1.copy()
    (e1 ne e2) && (e1 == e2) && (e1.## == e2.##)
  }

  private def extSizeTest(e: MsgpackExt) = {
    val bytes = MsgpackCodec[MsgpackUnion].toBytes(e, packer())
    e.data.length match {
      case n @ (1 | 2 | 4 | 8 | 16) =>
        assert(e.tpe == bytes(1))
        bytes.length == (n + 2) // header(1) + type(1) + data(n)
      case n if n < (1 << 8) =>
        assert(e.tpe == bytes(2))
        bytes.length == (n + 3) // header(1) + size(1) + type(1) + data(n)
      case n if n < (1 << 16) =>
        assert(e.tpe == bytes(3))
        bytes.length == (n + 4) // header(1) + size(2) + type(1) + data(n)
      case n =>
        assert(e.tpe == bytes(5))
        bytes.length == (n + 6) // header(1) + size(4) + type(1) + data(n)
    }
  }

  val extSize1 = Property.forAllG(UnionGen.extGen) { e =>
    if (supportExtType) {
      extSizeTest(e)
    } else true
  }

  property("extSize1") = extSize1
  property("extSize2") = extSize1.toProperties((), Param.maxSize(1 << 18))

  property("ext16") = Property.forAll {
    if (supportExtType) {
      val size = 1 << 10
      val e = MsgpackUnion.ext((Random.nextInt().toByte, Array.fill[Byte](size)(Random.nextInt().toByte)))
      val bytes = MsgpackCodec[MsgpackUnion].toBytes(e, packer())
      assert(bytes.length == (size + 4)) // header(1) + size(2) + type(1) + data(n)
      assert(bytes(0) == 0xc8.toByte)
      MsgpackCodec[MsgpackUnion].unpackAndClose(unpacker(bytes)) match {
        case \/-(a) =>
          a == e
        case -\/(a) =>
          println(a)
          throw a
      }
    } else true
  }

  property("ext32") = Property.forAll {
    if (supportExtType) {
      val size = 1 << 17
      val e = MsgpackUnion.ext((Random.nextInt().toByte, Array.fill[Byte](size)(Random.nextInt().toByte)))
      val bytes = MsgpackCodec[MsgpackUnion].toBytes(e, packer())
      assert(bytes.length == (size + 6)) // header(1) + size(4) + type(1) + data(n)
      assert(bytes(0) == 0xc9.toByte)
      MsgpackCodec[MsgpackUnion].unpackAndClose(unpacker(bytes)) match {
        case \/-(a) =>
          a == e
        case -\/(a) =>
          println(a)
          throw a
      }
    } else true
  }

  property("map imap") = Property.forAll { (a: Map[MsgpackUnion, MsgpackUnion]) =>
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
