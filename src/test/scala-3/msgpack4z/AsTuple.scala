package msgpack4z

import scala.deriving.Mirror.ProductOf

object AsTuple {
  extension [A <: Product](value: A) {
    def asTuple(using mirror: ProductOf[A]): mirror.MirroredElemTypes =
      Tuple.fromProductTyped(value)
  }
}
