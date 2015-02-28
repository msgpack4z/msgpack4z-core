package msgpack4z

final class Opt[A <: AnyRef] private[msgpack4z] (val get: A) extends AnyVal {
  def isEmpty: Boolean = get eq null
  def nonEmpty: Boolean = get ne null
  def toOption: Option[A] = Option(get)

  def map[B <: AnyRef](f: A => B): Opt[B] =
    if(isEmpty) Opt.empty[B]
    else new Opt(f(get))

  def orNull: A = get

  def exists(f: A => Boolean): Boolean =
    if(isEmpty) false
    else f(get)

  def forall(f: A => Boolean): Boolean =
    if(isEmpty) true
    else f(get)

  def filter(f: A => Boolean): Opt[A] =
    if(isEmpty) this
    else if(f(get)) this
    else Opt.empty[A]

  def flatMap[B <: AnyRef](f: A => Opt[B]): Opt[B] =
    if(isEmpty) Opt.empty[B]
    else f(get)

  def toList: List[A] =
    if(isEmpty) Nil
    else (get :: Nil)
}

object Opt {
  def empty[A <: AnyRef]: Opt[A] = new Opt(null.asInstanceOf[A])
}
