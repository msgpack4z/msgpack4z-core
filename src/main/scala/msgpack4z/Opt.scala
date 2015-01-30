package msgpack4z

final class Opt[A <: AnyRef] private[msgpack4z] (val get: A) extends AnyVal {
  def isEmpty: Boolean = get eq null
  def nonEmpty: Boolean = get ne null
  def toOption: Option[A] = Option(get)
}

object Opt {
  def empty[A <: AnyRef]: Opt[A] = new Opt(null.asInstanceOf[A])
}
