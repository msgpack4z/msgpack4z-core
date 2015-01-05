package msgpack4z

import org.scalacheck.Properties
import scalaz.Equal
import scalaz.std.anyVal._
import scalaz.std.list._
import scalaz.std.string._

abstract class ArraySpec(name: String) extends SpecBase(name + " array") {

  private implicit def arrayEq[A](implicit A: Equal[A]): Equal[Array[A]] =
    Equal.equalBy(_.toList)

  property("primitive") = new Properties("") {
    import CodecInstances.anyValArray._
    property("Short") = checkLawz[Array[Short]]
    property("Boolean") = checkLawz[Array[Boolean]]
    property("Int") = checkLawz[Array[Int]]
    property("Long") = checkLawz[Array[Long]]
    property("Float") = checkLawz[Array[Float]]
    property("Double") = checkLawz[Array[Double]]
    property("Byte") = checkLawzWithExcplicit[Array[Byte]](CodecInstances.byteArray.arrayByte)
  }

  property("generic") = new Properties("") {
    import CodecInstances.array.arrayCodec
    import CodecInstances.anyVal._
    import CodecInstances.std.stringCodec

    property("Short") = checkLawz[Array[Short]]
    property("Boolean") = checkLawz[Array[Boolean]]
    property("Int") = checkLawz[Array[Int]]
    property("Long") = checkLawz[Array[Long]]
    property("Float") = checkLawz[Array[Float]]
    property("Double") = checkLawz[Array[Double]]
    property("Byte") = checkLawz[Array[Byte]]
    property("String") = checkLawz[Array[String]]
  }

}
