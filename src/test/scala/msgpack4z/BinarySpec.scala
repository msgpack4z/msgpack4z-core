package msgpack4z

import msgpack4z.CodecInstances.binary._
import scala.util.Random
import scalaprops._
import scalaz.Equal

abstract class BinarySpec extends SpecBase {
  private implicit val binaryEq: Equal[Binary] =
    Equal.equal((a, b) => java.util.Arrays.equals(a.value, b.value))

  private implicit val binaryArb: Gen[Binary] =
    Gen[Array[Byte]].map(new Binary(_))

  val binary = checkLawz[Binary]

  val `binary 16` = checkLaw[Binary](
    implicitly,
    Gen.value(new Binary(Array.fill[Byte](1 << (8 + 1))(Random.nextInt.toByte)))
  )

  val `binary 32` = checkLaw[Binary](
    implicitly,
    Gen.value(new Binary(Array.fill[Byte](1 << (16 + 1))(Random.nextInt.toByte)))
  )
}
