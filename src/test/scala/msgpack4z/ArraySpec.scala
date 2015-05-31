package msgpack4z

import msgpack4z.UnionGen._
import scalaprops._
import scalaz.Equal
import scalaz.std.anyVal._
import scalaz.std.list._
import scalaz.std.string._

abstract class ArraySpec extends SpecBase{

  private implicit def arrayEq[A](implicit A: Equal[A]): Equal[Array[A]] =
    Equal.equalBy(_.toList)

  val primitive = {
    import msgpack4z.CodecInstances.anyValArray._
    Properties.list(
      checkLawWithoutHashCode[Array[Short]].toProperties("Short"),
      checkLawWithoutHashCode[Array[Boolean]].toProperties("Boolean"),
      checkLawWithoutHashCode[Array[Int]].toProperties("Int"),
      checkLawWithoutHashCode[Array[Long]].toProperties("Long"),
      checkLawWithoutHashCode[Array[Float]].toProperties("Float"),
      checkLawWithoutHashCode[Array[Double]].toProperties("Double"),
      checkLawWithoutHashCode[Array[Byte]](CodecInstances.byteArray.arrayByte, implicitly, arrayEq[Byte]).toProperties("Byte")
    )
  }

  val generic = {
    import CodecInstances.array.arrayCodec
    import CodecInstances.anyVal._
    import CodecInstances.std.stringCodec

    Properties.list(
      checkLawWithoutHashCode[Array[Short]].toProperties("Short"),
      checkLawWithoutHashCode[Array[Boolean]].toProperties("Boolean"),
      checkLawWithoutHashCode[Array[Int]].toProperties("Int"),
      checkLawWithoutHashCode[Array[Long]].toProperties("Long"),
      checkLawWithoutHashCode[Array[Float]].toProperties("Float"),
      checkLawWithoutHashCode[Array[Double]].toProperties("Double"),
      checkLawWithoutHashCode[Array[Byte]].toProperties("Byte"),
      checkLawWithoutHashCode[Array[String]].toProperties("String")
    )
  }

}
