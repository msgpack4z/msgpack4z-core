package msgpack4z

import scala.util.control.NonFatal
import scalaz._

trait MsgpackCodec[A] {

  def pack(packer: MsgPacker, a: A): Unit

  def packF: Packer[A] =
    pack

  final def toBytes(a: A, packer: MsgPacker): Array[Byte] = {
    pack(packer, a)
    packer.result
  }

  final def unpackAndClose(unpacker: MsgUnpacker): UnpackResult[A] =
    try {
      unpack(unpacker)
    } finally {
      unpacker.close()
    }

  def unpack(unpacker: MsgUnpacker): UnpackResult[A]

  def unpackF: Unpacker[A] =
    unpack

  final def xmap[B](f: A => B, g: B => A): MsgpackCodec[B] =
    MsgpackCodec.codec[B]((packer, a) => pack(packer, g(a)), unpackF.andThen(_.map(f)))

  final def roundtripz(a: A, packer: MsgPacker, unpacker: Array[Byte] => MsgUnpacker)(implicit A: Equal[A]): Option[UnpackError \/ A] = {
    val u = unpacker(toBytes(a, packer))
    unpackAndClose(u) match {
      case aa @ \/-(a0) =>
        if (A.equal(a, a0))
          None
        else
          Some(aa)
      case e @ -\/(_) =>
        Some(e)
    }
  }

  /**
   * @return
   *   `None` if success
   *   `Some(-\/(_))` if `fromBytes` returns deserialize error
   *   `Some(\/-(_))` if `fromBytes` and `toByte` are inconsistent
   */
  final def roundtrip(a: A, packer: MsgPacker, unpacker: Array[Byte] => MsgUnpacker): Option[UnpackError \/ A] =
    roundtripz(a, packer, unpacker)(Equal.equalA[A])
}

private class MsgpackCodecConstant[A](
  override val packF: Packer[A],
  override val unpackF: Unpacker[A]
) extends MsgpackCodec[A] {

  override def pack(packer: MsgPacker, a: A) =
    packF(packer, a)

  override def unpack(unpacker: MsgUnpacker): UnpackResult[A] =
    unpackF(unpacker)

}

object MsgpackCodec {

  @inline def apply[A](implicit A: MsgpackCodec[A]): MsgpackCodec[A] = A

  /**
   * @example {{{
   * case class UserId(value: Int)
   *
   * object UserId {
   *   implicit val msgpackCodec: MsgpackCodec[UserId] =
   *     MsgpackCodec.from(apply, unapply)
   * }
   * }}}
   */
  def from[A, B](applyFunc: A => B, unapplyFunc: B => Option[A])(implicit A: MsgpackCodec[A]): MsgpackCodec[B] =
    A.xmap(applyFunc, Function.unlift(unapplyFunc))

  def codec[A](packerF: Packer[A], unpackerF: Unpacker[A]): MsgpackCodec[A] =
    new MsgpackCodec[A] {
      override def pack(packer: MsgPacker, a: A) =
        packerF(packer, a)

      override def unpack(unpacker: MsgUnpacker) =
        unpackerF(unpacker)
    }

  def codecTry[A](packF: Packer[A], unpackF: MsgUnpacker => A): MsgpackCodec[A] =
    tryE[A](
      packF,
      u => \/-(unpackF(u))
    )

  private[msgpack4z] def tryE[A](packF: Packer[A], unpackF: MsgUnpacker => UnpackResult[A]): MsgpackCodec[A] = codec[A](
    packF,
    u =>
      try {
        unpackF(u)
      } catch {
        case NonFatal(e) =>
          -\/(Err(e))
      }
  )

  private[msgpack4z] def tryConst[A](packF: Packer[A], unpackF: MsgUnpacker => A): MsgpackCodec[A] =
    tryConstE(packF, u => \/-(unpackF(u)))

  private[msgpack4z] def tryConstE[A](packF: Packer[A], unpackF: MsgUnpacker => UnpackResult[A]): MsgpackCodec[A] =
    new MsgpackCodecConstant[A](
      packF,
      u =>
        try {
          unpackF(u)
        } catch {
          case NonFatal(e) =>
            -\/(Err(e))
        }
    )
}
