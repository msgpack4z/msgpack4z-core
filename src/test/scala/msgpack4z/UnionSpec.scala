package msgpack4z

import org.scalacheck.{Arbitrary, Prop}

abstract class UnionSpec(name: String) extends SpecBase(name + " union") {

  property("union") = Prop.secure{
    checkLaw(MsgpackUnion.codecInstance, Arbitrary(UnionArbitrary.ulongGen))
  }

}
