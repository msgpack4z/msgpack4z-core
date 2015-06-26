package msgpack4z

/**
 * @example {{{
 * case class UserId(value: Int)
 *
 * object UserId extends MsgpackCompanion[Int, UserId]
 * }}}
 */
abstract class MsgpackCompanion[A, B](implicit A: MsgpackCodec[A]) extends (A => B){
  implicit final val msgpackCodec: MsgpackCodec[B] =
    MsgpackCodec.from(apply, unapply)

  def unapply(value: B): Option[A]
}
