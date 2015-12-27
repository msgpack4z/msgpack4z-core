package msgpack4z

import scalaprops._
import scala.util.control.NonFatal
import scalaz._

abstract class SpecBase extends Scalaprops {

  protected[this] final def expectException[A](f: => A): Unit = {
    val ex = new Exception("expect Exception")
    try {
      val _ = f
      throw ex
    } catch {
      case NonFatal(e) if e ne ex =>
    }
  }

  protected[this] def packer(): MsgPacker
  protected[this] def unpacker(bytes: Array[Byte]): MsgUnpacker

  implicit final val symbolGen: Gen[Symbol] =
    Tag.unsubst(Gen[String @@ GenTags.AlphaNum]).map(Symbol(_))

  implicit final val stringGen: Gen[String] =
    Tag.unsubst(Gen[String @@ GenTags.AlphaNum])

  final def checkRoundTripBytes[A](checkHashCode: Boolean)(implicit A: MsgpackCodec[A], G: Gen[A], E: Equal[A]): Property =
    Property.forAll { a: A =>
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
      } catch {
        case NonFatal(e) =>
          println(a)
          println(e.getStackTrace.map("\tat " + _).mkString("\n" + e.toString + "\n", "\n", "\n"))
          throw e
      }
    }


  final def checkLawz[A](implicit A: MsgpackCodec[A], G: Gen[A], E: Equal[A]) =
    checkRoundTripBytes(true)(A, G, E)

  final def checkLaw[A](implicit A: MsgpackCodec[A], G: Gen[A]) =
    checkRoundTripBytes(true)(A, G, Equal.equalA[A])

  final def checkLawWithoutHashCode[A](implicit A: MsgpackCodec[A], G: Gen[A], E: Equal[A]) =
    checkRoundTripBytes(false)(A, G, E)


}
