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
    property("Short") = checkLawWithoutHashCode[Array[Short]]
    property("Boolean") = checkLawWithoutHashCode[Array[Boolean]]
    property("Int") = checkLawWithoutHashCode[Array[Int]]
    property("Long") = checkLawWithoutHashCode[Array[Long]]
    property("Float") = checkLawWithoutHashCode[Array[Float]]
    property("Double") = checkLawWithoutHashCode[Array[Double]]
    property("Byte") = checkLawWithoutHashCode[Array[Byte]](CodecInstances.byteArray.arrayByte, implicitly, arrayEq[Byte])
  }

  property("generic") = new Properties("") {
    import CodecInstances.array.arrayCodec
    import CodecInstances.anyVal._
    import CodecInstances.std.stringCodec

    property("Short") = checkLawWithoutHashCode[Array[Short]]
    property("Boolean") = checkLawWithoutHashCode[Array[Boolean]]
    property("Int") = checkLawWithoutHashCode[Array[Int]]
    property("Long") = checkLawWithoutHashCode[Array[Long]]
    property("Float") = checkLawWithoutHashCode[Array[Float]]
    property("Double") = checkLawWithoutHashCode[Array[Double]]
    property("Byte") = checkLawWithoutHashCode[Array[Byte]]
    property("String") = checkLawWithoutHashCode[Array[String]]
  }

}
