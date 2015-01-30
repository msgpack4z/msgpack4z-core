package msgpack4z

import scalaz.NonEmptyList

sealed abstract class CaseClassMapMissingKeyError extends RuntimeException{
  type K
  val keys: NonEmptyList[K]
  val codec: MsgpackCodec[K]
}

private[msgpack4z] object CaseClassMapMissingKeyError {
  def apply[K0](keys0: NonEmptyList[K0], codec0: MsgpackCodec[K0]): CaseClassMapMissingKeyError =
    new CaseClassMapMissingKeyError {
      override type K = K0
      override val keys = keys0
      override val codec = codec0
    }
}
