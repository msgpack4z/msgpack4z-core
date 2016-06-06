package msgpack4z

sealed abstract class UnpackError(message: String, cause: Throwable) extends Exception(message, cause)

final case class Err(e: Throwable) extends UnpackError(e.getMessage, e)

final case class Other(message: String) extends UnpackError(message, null)

final case class UnexpectedArraySize(expect: Int, actual: Int) extends UnpackError(s"expect = $expect, actual = $actual", null)

final case class NotEnoughArraySize(expect: Int, actual: Int) extends UnpackError(s"expect = $expect, actual = $actual", null)

final case class UnexpectedMapSize(expect: Int, actual: Int) extends UnpackError(s"expect = $expect, actual = $actual", null)

final case class UnexpectedEitherKey[A](left: A, right: A, actual: A)(implicit A: MsgpackCodec[A])
  extends UnpackError(s"expect = $left or $right, actual = $actual", null)

final case class MapKeyNotFound(key1: String, key2: String) extends UnpackError("", null)
