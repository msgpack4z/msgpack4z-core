package msgpack4z

import org.scalacheck.{Gen, Arbitrary}
import scala.util.Random
import scalaz.{Functor, Equal}
import scalaz.scalacheck.ScalaCheckBinding._
import msgpack4z.CodecInstances.binary._

abstract class BinarySpec(name: String) extends SpecBase(name + " binary") {

  private implicit val binaryEq: Equal[Binary] =
    Equal.equal((a, b) => java.util.Arrays.equals(a.value, b.value))

  private implicit val binaryArb: Arbitrary[Binary] =
    Functor[Arbitrary].map(implicitly[Arbitrary[Array[Byte]]])(new Binary(_))

  property("Binary") = checkLawz[Binary]

  property("binary 16") = checkLaw[Binary](
    implicitly,
    Arbitrary(Gen.const(new Binary(Array.fill[Byte](1 << (8 + 1))(Random.nextInt.toByte))))
  )

  property("binary 32") = checkLaw[Binary](
    implicitly,
    Arbitrary(Gen.const(new Binary(Array.fill[Byte](1 << (16 + 1))(Random.nextInt.toByte))))
  )

}
