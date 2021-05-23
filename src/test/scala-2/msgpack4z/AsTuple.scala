package msgpack4z

import shapeless.ops.product.ToTuple

object AsTuple {
  implicit class AsTupleOps[A](private val value: A) extends AnyVal {
    def asTuple[B](implicit toTuple: ToTuple.Aux[A, B]): B =
      toTuple.apply(value)
  }
}
