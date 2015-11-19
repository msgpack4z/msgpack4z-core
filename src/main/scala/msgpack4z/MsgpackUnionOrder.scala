package msgpack4z

import java.math.BigInteger
import scalaz.Order
import scalaz.Ordering._
import scalaz.std.anyVal._
import scalaz.std.java.math.bigInteger._
import scalaz.std.string._

private[msgpack4z] object MsgpackUnionOrder extends Order[MsgpackUnion] {

  override def equalIsNatural = true
  override val toScalaOrdering = super.toScalaOrdering

  override def equal(x: MsgpackUnion, y: MsgpackUnion) =
    x === y

  private[this] val UnionListOrder: Order[List[MsgpackUnion]] =
    scalaz.std.list.listOrder(this)

  private[this] val UnionMapOrder: Order[Map[MsgpackUnion, MsgpackUnion]] =
    scalaz.std.map.mapOrder(this, this)

  override def order(x: MsgpackUnion, y: MsgpackUnion) = x match {
    case MsgpackTrue =>
      y match {
        case MsgpackTrue =>
          EQ
        case _ =>
          GT
      }
    case MsgpackFalse =>
      y match {
        case MsgpackTrue =>
          LT
        case MsgpackFalse =>
          EQ
        case _ =>
          GT
      }
    case MsgpackNil =>
      y match {
        case MsgpackTrue | MsgpackFalse =>
          LT
        case MsgpackNil =>
          EQ
        case _ =>
          GT
      }
    case MsgpackLong(xx) =>
      y match {
        case MsgpackTrue | MsgpackFalse | MsgpackNil =>
          LT
        case MsgpackLong(yy) =>
          Order[Long].order(xx, yy)
        case _ =>
          GT
      }
    case MsgpackString(xx) =>
      y match {
        case MsgpackTrue | MsgpackFalse | MsgpackNil | (_: MsgpackLong) =>
          LT
        case MsgpackString(yy) =>
          Order[String].order(xx, yy)
        case _ =>
          GT
      }
    case MsgpackBinary(xx) =>
      y match {
        case MsgpackTrue | MsgpackFalse | MsgpackNil | (_: MsgpackLong) | (_: MsgpackString) =>
          LT
        case MsgpackBinary(yy) =>
          compareArrayByte(xx, yy)
        case _ =>
          GT
      }
    case MsgpackDouble(xx) =>
      y match {
        case MsgpackTrue | MsgpackFalse | MsgpackNil | MsgpackLong(_) | MsgpackString(_) | MsgpackBinary(_) =>
          LT
        case MsgpackDouble(yy) =>
          Order[Long].order(java.lang.Double.doubleToLongBits(xx), java.lang.Double.doubleToLongBits(yy))
        case _ =>
          GT
      }
    case MsgpackULong(xx) =>
      y match {
        case (_: MsgpackMap) | (_: MsgpackArray) | MsgpackExt(_, _) =>
          GT
        case MsgpackULong(yy) =>
          Order[BigInteger].order(xx, yy)
        case _ =>
          LT
      }
    case MsgpackArray(xx) =>
      y match {
        case (_: MsgpackMap) | MsgpackExt(_, _) =>
          GT
        case MsgpackArray(yy) =>
          UnionListOrder.order(xx, yy)
        case _ =>
          LT
      }
    case MsgpackMap(xx) =>
      y match {
        case MsgpackExt(_, _) =>
          GT
        case MsgpackMap(yy) =>
          UnionMapOrder.order(xx, yy)
        case _ =>
          LT
      }
    case MsgpackExt(type1, data1) =>
      y match {
        case MsgpackExt(type2, data2) =>
          Order[Byte].order(type1, type2) match {
            case scalaz.Ordering.EQ =>
              compareArrayByte(data1, data2)
            case other =>
              other
          }
        case _ =>
          LT
      }
  }

  private[this] def compareArrayByte(xx: Array[Byte], yy: Array[Byte]): scalaz.Ordering = {
    if (xx.length == yy.length) {
      @annotation.tailrec
      def loop(i: Int): scalaz.Ordering = {
        if (i < xx.length) {
          val a = xx(i)
          val b = yy(i)
          if (a < b) {
            scalaz.Ordering.LT
          } else if (a > b) {
            scalaz.Ordering.GT
          } else {
            loop(i + 1)
          }
        } else EQ
      }
      loop(0)
    } else if (xx.length < yy.length) {
      LT
    } else {
      GT
    }
  }

}
