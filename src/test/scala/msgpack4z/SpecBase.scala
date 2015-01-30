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

  final def checkRoundTripBytes[A](checkHashCode: Boolean)(implicit A: MsgpackCodec[A], G: Arbitrary[A], E: Equal[A]): Prop =
    Prop.forAll{ a: A =>
      Prop.secure{
        try {
          A.roundtripz(a, packer(), unpacker _) match {
            case None =>
              if (checkHashCode) {
                A.unpackAndClose(unpacker(A.toBytes(a, packer()))) match {
                  case \/-(value) =>
                    a.## == value.##
                  case -\/(e) =>
                    throw e
                }
              } else {
                true
              }
            case Some(\/-(b)) =>
              println("fail roundtrip bytes " + a + " " + b)
              false
            case Some(-\/(e)) =>
              println(e)
              false
          }
        }catch{
          case NonFatal(e) =>
            println(a)
            println(e.getStackTrace.map("\tat " + _).mkString("\n" + e.toString + "\n","\n", "\n"))
            throw e
        }
      }
    }


  final def checkLawz[A](implicit A: MsgpackCodec[A], G: Arbitrary[A], E: Equal[A]) =
    checkRoundTripBytes(true)(A, G, E)

  final def checkLaw[A](implicit A: MsgpackCodec[A], G: Arbitrary[A]) =
    checkRoundTripBytes(true)(A, G, Equal.equalA[A])

  final def checkLawWithoutHashCode[A](implicit A: MsgpackCodec[A], G: Arbitrary[A], E: Equal[A]) =
    checkRoundTripBytes(false)(A, G, E)


}
