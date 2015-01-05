package msgpack4z

import org.scalacheck.{Arbitrary, Prop, Properties}
import scala.util.control.NonFatal
import scalaz.scalacheck.ScalaCheckBinding._
import scalaz.{-\/, Equal, Functor, \/-}

abstract class SpecBase(name: String) extends Properties(name) {

  protected[this] def packer(): MsgPacker
  protected[this] def unpacker(bytes: Array[Byte]): MsgUnpacker

  implicit private val alpha = Arbitrary(org.scalacheck.Gen.alphaStr)

  implicit final val symbolArb: Arbitrary[Symbol] =
    Functor[Arbitrary].map(implicitly[Arbitrary[String]])(Symbol(_))

  final def checkLawzWithExcplicit[A](A: MsgpackCodec[A])(implicit G: Arbitrary[A], E: Equal[A]) =
    checkLawz[A](A, G, E)

  private def checkRoundTripBytes[A](a: A)(implicit A: MsgpackCodec[A], G: Arbitrary[A], E: Equal[A]): Boolean = {
    A.roundtripz(a, packer(), unpacker _) match {
      case None =>
        true
      case Some(\/-(b)) =>
        println("fail roundtrip bytes " + a + " " + b)
        false
      case Some(-\/(e)) =>
        println(e)
        false
    }
  }

  final def checkLawz[A](implicit A: MsgpackCodec[A], G: Arbitrary[A], E: Equal[A]): Prop =
    Prop.forAll{ (a: A) =>
      Prop.secure{
        try {
          checkRoundTripBytes(a)
        }catch{
          case NonFatal(e) =>
            println(a)
            println(e.getStackTrace.map("\tat " + _).mkString("\n" + e.toString + "\n","\n", "\n"))
            throw e
        }
      }
    }

  final def checkLaw[A](implicit A: MsgpackCodec[A], G: Arbitrary[A]) =
    checkLawz(A, G, Equal.equalA[A])

}
