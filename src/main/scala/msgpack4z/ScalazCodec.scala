package msgpack4z

import scalaz._

trait ScalazCodec {

  implicit def iListCodec[A](implicit A: MsgpackCodec[A]): MsgpackCodec[IList[A]]
  implicit def imapCodec[A, B](implicit O: Order[A], A: MsgpackCodec[A], B: MsgpackCodec[B]): MsgpackCodec[A ==>> B]
  implicit def isetCodec[A](implicit O: Order[A], A: MsgpackCodec[A]): MsgpackCodec[ISet[A]]
  implicit def nonEmptyListCodec[A](implicit A: MsgpackCodec[A]): MsgpackCodec[NonEmptyList[A]]
  implicit def maybeCodec[A](implicit A: MsgpackCodec[A]): MsgpackCodec[Maybe[A]]

}

private[msgpack4z] trait ScalazCodecImpl extends ScalazCodec {

  override final def iListCodec[A](implicit A: MsgpackCodec[A]): MsgpackCodec[IList[A]] = MsgpackCodec.tryE(
    (packer, list) => {
      packer.packArrayHeader(list.length)
      @annotation.tailrec
      def loop(x: IList[A]): Unit = x match {
        case ICons(h, t) =>
          A.pack(packer, h)
          loop(t)
        case _ =>
      }
      loop(list)
      packer.arrayEnd()
    }
    ,
    unpacker => {
      val size = unpacker.unpackArrayHeader()
      var list: IList[A] = INil()
      var i = 0
      var error: -\/[UnpackError] = null
      while(i < size && error == null){
        A.unpack(unpacker) match {
          case \/-(a) =>
            list ::= a
          case e @ -\/(_) =>
            error = e
        }
        i += 1
      }
      unpacker.arrayEnd()
      if(error == null)
        \/-(list.reverse)
      else
        error
    }
  )

  override final def isetCodec[A](implicit O: Order[A], A: MsgpackCodec[A]): MsgpackCodec[ISet[A]] = MsgpackCodec.tryE(
    (packer, set) => {
      packer.packArrayHeader(set.size)
      set.foldLeft(())((_, a) => A.pack(packer, a))
      packer.arrayEnd()
    }
    ,
    unpacker => {
      val size = unpacker.unpackArrayHeader()
      var set = ISet.empty[A]
      var i = 0
      var error: -\/[UnpackError] = null
      while(i < size && error == null){
        A.unpack(unpacker) match {
          case \/-(a) =>
            set = set.insert(a)
          case e @ -\/(_) =>
            error = e
        }
        i += 1
      }
      unpacker.arrayEnd()
      if(error == null)
        \/-(set)
      else
        error
    }
  )

  override final def imapCodec[A, B](implicit O: Order[A], A: MsgpackCodec[A], B: MsgpackCodec[B]): MsgpackCodec[A ==>> B] = {
    MsgpackCodec.tryE(
      (packer, m) => {
        packer.packMapHeader(m.size)
        m.fold(()){ (key, value, _) =>
          A.pack(packer, key)
          B.pack(packer, value)
        }
        packer.mapEnd()
      }
      ,
      unpacker => {
        val size = unpacker.unpackMapHeader()
        var i = 0
        var error: -\/[UnpackError] = null
        var imap: A ==>> B = ==>>.empty
        while(i < size && error == null){
          A.unpack(unpacker) match {
            case \/-(k) =>
              B.unpack(unpacker) match {
                case \/-(v) =>
                  imap = imap.insert(k, v)
                case e @ -\/(_) =>
                  error = e
              }
            case e @ -\/(_) =>
              error = e
          }
          i += 1
        }
        unpacker.mapEnd()
        if(error == null)
          \/-(imap)
        else
          error
      }
    )
  }

  override final def nonEmptyListCodec[A](implicit A: MsgpackCodec[A]): MsgpackCodec[NonEmptyList[A]] = MsgpackCodec.tryE(
    (packer, nel) => {
      packer.packArrayHeader(nel.size)
      A.pack(packer, nel.head)
      var list: List[A] = nel.tail
      while(list ne Nil){
        A.pack(packer, list.head)
        list = list.tail
      }
      packer.arrayEnd()
    }
    ,
    unpacker => {
      val size = unpacker.unpackArrayHeader()
      if(size >= 1) {
        A.unpack(unpacker) match {
          case \/-(h) =>
            var list: List[A] = Nil
            var i = 1
            var error: -\/[UnpackError] = null
            while (i < size && error == null) {
              A.unpack(unpacker) match {
                case \/-(a) =>
                  list ::= a
                case e @ -\/(_) =>
                  error = e
              }
              i += 1
            }
            unpacker.arrayEnd()
            if(error == null)
              \/-(NonEmptyList.nel(h, list.reverse))
            else
              error
          case e @ -\/(_) =>
            e
        }
      }else{
        -\/(NotEnoughArraySize(1, 0))
      }
    }
  )

  override final def maybeCodec[A](implicit A: MsgpackCodec[A]): MsgpackCodec[Maybe[A]] =
    MaybeCodec.maybeCompactCodec[A]
}
