package msgpack4z

import msgpack4z.MsgpackCodec.tryConst
import scalaz.{-\/, \/-}

trait StdCodec {
  implicit def listCodec[A: MsgpackCodec]: MsgpackCodec[List[A]]
  implicit def mapCodec[A: MsgpackCodec, B: MsgpackCodec]: MsgpackCodec[Map[A, B]]
  implicit def vectorCodec[A: MsgpackCodec]: MsgpackCodec[Vector[A]]
  /** @see [[https://github.com/msgpack/msgpack/blob/master/spec.md#formats-str]] */
  implicit def stringCodec: MsgpackCodec[String]
  implicit def symbolCodec: MsgpackCodec[Symbol]
  implicit def optionCodec[A: MsgpackCodec]: MsgpackCodec[Option[A]]
}

private[msgpack4z] object AllImpl
  extends AnyValCodecImpl
  with TupleCodecImpl
  with AnyValArrayCodecImpl
  with RefArrayCodecImpl
  with StdCodec
  with BinaryCodecImpl
  with ScalazCodecImpl
{

  override implicit def listCodec[A](implicit A: MsgpackCodec[A]): MsgpackCodec[List[A]] = MsgpackCodec.tryE(
    { (packer, list) =>
      packer.packArrayHeader(list.length)
      var x: List[A] = list
      while (x ne Nil){
        A.pack(packer, x.head)
        x = x.tail
      }
      packer.arrayEnd()
    },
    { unpacker =>
      val size = unpacker.unpackArrayHeader()
      var list: List[A] = Nil
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

  override implicit def mapCodec[A, B](implicit A: MsgpackCodec[A], B: MsgpackCodec[B]): MsgpackCodec[Map[A, B]] =
    MsgpackCodec.tryE(
      (packer, m) => {
        packer.packMapHeader(m.size)
        m.foreach{ keyValue =>
          A.pack(packer, keyValue._1)
          B.pack(packer, keyValue._2)
        }
        packer.mapEnd()
      }
      ,
      unpacker => {
        val size = unpacker.unpackMapHeader()
        var i = 0
        val builder = Map.newBuilder[A, B]
        var error: -\/[UnpackError] = null
        while(i < size && error == null){
          A.unpack(unpacker) match {
            case \/-(k) =>
              B.unpack(unpacker) match {
                case \/-(v) =>
                  builder += (k -> v)
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
          \/-(builder.result())
        else
          error
      }
    )

  override implicit val stringCodec: MsgpackCodec[String] =
    tryConst(_ packString _, _.unpackString)

  override implicit val symbolCodec: MsgpackCodec[Symbol] =
    tryConst(_ packString _.name, x => Symbol(x.unpackString))


  override implicit def vectorCodec[A](implicit A: MsgpackCodec[A]): MsgpackCodec[Vector[A]] = MsgpackCodec.tryE(
    { (packer, vector) =>
      packer.packArrayHeader(vector.length)
      vector.foreach{ x =>
        A.pack(packer, x)
      }
      packer.arrayEnd()
    },
    { unpacker =>
      val size = unpacker.unpackArrayHeader()
      val builder = Vector.newBuilder[A]
      var i = 0
      var error: -\/[UnpackError] = null
      while(i < size && error == null){
        A.unpack(unpacker) match {
          case \/-(a) =>
            builder += a
          case e @ -\/(_) =>
            error = e
        }
        i += 1
      }
      unpacker.arrayEnd()
      if(error == null)
        \/-(builder.result())
      else
        error
    }
  )

  override implicit def optionCodec[A: MsgpackCodec]: MsgpackCodec[Option[A]] =
    OptionCodec.optionCompactCodec[A]
}
