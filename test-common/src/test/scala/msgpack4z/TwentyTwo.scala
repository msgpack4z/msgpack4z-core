package msgpack4z

import scalaprops.Gen

final case class TwentyTwo(
  _1 : Int,
  _2 : Int,
  _3 : List[Int],
  _4 : Int,
  _5 : Boolean,
  _6 : Int,
  _7 : Int,
  _8 : Byte,
  _9 : Int,
  _10 : Int,
  _11 : Boolean,
  _12 : Int,
  _13 : Int,
  _14 : Option[Int],
  _15 : Int,
  _16 : Int,
  _17 : Long,
  _18 : Int,
  _19 : Int,
  _20 : Map[Long, Long],
  _21 : Int,
  _22 : Int
)

object TwentyTwo {

  implicit val gen: Gen[TwentyTwo] = Gen.from22(TwentyTwo.apply)

}
