package msgpack4z

import java.math.BigInteger
import msgpack4z.MsgpackUnion.constNone
import scalaz.{IMap, Order}

sealed abstract class MsgpackUnion extends Product with Serializable {
  final def string: Opt[String] =
    this match {
      case MsgpackString(value) => new Opt(value)
      case _ => Opt.empty[String]
    }

  final def binary: Opt[Array[Byte]] =
    this match {
      case MsgpackBinary(value) => new Opt(value)
      case _ => Opt.empty
    }

  final def long: Option[Long] =
    this match {
      case MsgpackLong(value) => Some(value)
      case _ => None
    }

  final def ulong: Opt[BigInteger] =
    this match {
      case MsgpackULong(value) => new Opt(value)
      case _ => Opt.empty
    }

  final def double: Option[Double] =
    this match {
      case MsgpackDouble(value) => Some(value)
      case _ => None
    }

  final def array: Opt[List[MsgpackUnion]] =
    this match {
      case MsgpackArray(value) => new Opt(value)
      case _ => Opt.empty
    }

  final def vector: Opt[Vector[MsgpackUnion]] =
    this match {
      case MsgpackArray(value) => new Opt(value.toVector)
      case _ => Opt.empty
    }

  final def map: Opt[Map[MsgpackUnion, MsgpackUnion]] =
    this match {
      case MsgpackMap(value) => new Opt(value)
      case _ => Opt.empty
    }

  final def ext: Opt[(Byte, Array[Byte])] =
    this match {
      case MsgpackExt(tpe, data) => new Opt((tpe, data))
      case _ => Opt.empty
    }

  final def imap: Opt[IMap[MsgpackUnion, MsgpackUnion]] =
    this match {
      case MsgpackMap(value) =>
        val i = value.iterator
        @annotation.tailrec
        def loop(acc: IMap[MsgpackUnion, MsgpackUnion]): IMap[MsgpackUnion, MsgpackUnion] = {
          if (i.hasNext) {
            loop(acc + i.next())
          } else {
            acc
          }
        }
        new Opt(loop(IMap.empty))
      case _ =>
        Opt.empty
    }

  final def bool: Option[Boolean] =
    this match {
      case MsgpackTrue => Some(true)
      case MsgpackFalse => Some(false)
      case _ => None
    }

  final def isNil: Boolean =
    this.isInstanceOf[MsgpackNil.type]

  final def isTrue: Boolean =
    this eq MsgpackTrue

  final def isFalse: Boolean =
    this eq MsgpackFalse

  final def isNaN: Boolean =
    this match {
      case MsgpackDouble(value) =>
        java.lang.Double.isNaN(value)
      case _ =>
        false
    }

  final def fold[A](
    string: String => A,
    binary: Array[Byte] => A,
    long: Long => A,
    ulong: BigInteger => A,
    double: Double => A,
    array: List[MsgpackUnion] => A,
    map: Map[MsgpackUnion, MsgpackUnion] => A,
    bool: Boolean => A,
    ext: (Byte, Array[Byte]) => A,
    nil: => A
  ): A =
    this match {
      case MsgpackLong(value) =>
        long(value)
      case MsgpackString(value) =>
        string(value)
      case MsgpackTrue =>
        bool(true)
      case MsgpackFalse =>
        bool(false)
      case MsgpackNil =>
        nil
      case MsgpackArray(value) =>
        array(value)
      case MsgpackMap(value) =>
        map(value)
      case MsgpackBinary(value) =>
        binary(value)
      case MsgpackULong(value) =>
        ulong(value)
      case MsgpackDouble(value) =>
        double(value)
      case MsgpackExt(tpe, value) =>
        ext(tpe, value)
    }

  final def foldOpt[A](
    string: String => Option[A] = constNone,
    binary: Array[Byte] => Option[A] = constNone,
    long: Long => Option[A] = constNone,
    ulong: BigInteger => Option[A] = constNone,
    double: Double => Option[A] = constNone,
    array: List[MsgpackUnion] => Option[A] = constNone,
    map: Map[MsgpackUnion, MsgpackUnion] => Option[A] = constNone,
    bool: Boolean => Option[A] = constNone,
    ext: (Byte, Array[Byte]) => Option[A] = (_: Byte, _: Array[Byte]) => None,
    nil: Option[A] = None
  ): Option[A] =
    fold[Option[A]](
      string,
      binary,
      long,
      ulong,
      double,
      array,
      map,
      bool,
      ext,
      nil
    )

  protected[msgpack4z] def pack(packer: MsgPacker): Unit

  final def ===(that: MsgpackUnion): Boolean =
    this.equals(that)

  final def as[A](factory: PackerUnpackerFactory)(implicit A: MsgpackCodec[A]): UnpackResult[A] = {
    val p = factory.packer
    pack(p)
    A.unpack(factory.unpacker(p.result()))
  }
}

object MsgpackUnion {
  private[this] val returnConstNone = (_: Any) => None
  private def constNone[A, B]: A => Option[B] = returnConstNone.asInstanceOf[A => Option[B]]

  private[this] val LongMax = BigInteger.valueOf(Long.MaxValue)
  private[this] val LongMin = BigInteger.valueOf(Long.MinValue)

  implicit val msgpackUnionInstance: Order[MsgpackUnion] =
    MsgpackUnionOrder

  val unpackF: MsgUnpacker => MsgpackUnion = unpack

  implicit val codecInstance: MsgpackCodec[MsgpackUnion] =
    MsgpackCodec.codecTry[MsgpackUnion](
      (packer, value) => value.pack(packer),
      unpacker => unpack(unpacker)
    )

  def unpack(unpacker: MsgUnpacker): MsgpackUnion = {
    unpacker.nextType() match {
      case MsgType.NIL =>
        unpacker.unpackNil()
        MsgpackNil
      case MsgType.BOOLEAN =>
        if (unpacker.unpackBoolean())
          MsgpackTrue
        else
          MsgpackFalse
      case MsgType.INTEGER =>
        val i = unpacker.unpackBigInteger()
        if (LongMin.compareTo(i) <= 0 && i.compareTo(LongMax) <= 0) {
          MsgpackLong(i.longValue())
        } else {
          MsgpackULong(i)
        }
      case MsgType.FLOAT =>
        MsgpackDouble(unpacker.unpackDouble())
      case MsgType.STRING =>
        MsgpackString(unpacker.unpackString())
      case MsgType.ARRAY =>
        val size = unpacker.unpackArrayHeader()
        val array = new Array[MsgpackUnion](size)
        var i = 0
        while (i < size) {
          array(i) = unpack(unpacker)
          i += 1
        }
        unpacker.arrayEnd()
        var list: List[MsgpackUnion] = Nil
        i -= 1
        while (i >= 0) {
          list = new ::(array(i), list)
          i -= 1
        }
        MsgpackArray(list)
      case MsgType.MAP =>
        val size = unpacker.unpackMapHeader()
        val builder = Map.newBuilder[MsgpackUnion, MsgpackUnion]
        builder.sizeHint(size)
        var i = 0
        while (i < size) {
          val key = unpack(unpacker)
          val value = unpack(unpacker)
          builder += (key -> value)
          i += 1
        }
        unpacker.mapEnd()
        MsgpackMap(builder.result())
      case MsgType.BINARY =>
        new MsgpackBinary(unpacker.unpackBinary())
      case MsgType.EXTENSION =>
        val header = unpacker.unpackExtTypeHeader()
        new MsgpackExt(header.getType, unpacker.readPayload(header.getLength))
    }
  }

  val string: Extractor[String] = new Extractor[String] {
    override def unapply(value: MsgpackUnion) =
      value.string
    override def apply(value: String): MsgpackUnion =
      new MsgpackString(value)
  }
  val binary: Extractor[Array[Byte]] = new Extractor[Array[Byte]] {
    override def unapply(value: MsgpackUnion) =
      value.binary
    override def apply(value: Array[Byte]): MsgpackUnion =
      new MsgpackBinary(value)
  }
  val byte: Byte => MsgpackUnion = i => MsgpackLong(i)
  val short: Short => MsgpackUnion = i => MsgpackLong(i)
  val int: Int => MsgpackUnion = i => MsgpackLong(i)
  val long: Long => MsgpackUnion = MsgpackLong
  val ulong: Extractor[BigInteger] = new Extractor[BigInteger] {
    override def unapply(value: MsgpackUnion) =
      value.ulong
    override def apply(value: BigInteger): MsgpackUnion =
      new MsgpackULong(value)
  }
  val float: Float => MsgpackUnion = i => MsgpackDouble(i)
  val double: Double => MsgpackUnion = MsgpackDouble
  val array: Extractor[List[MsgpackUnion]] = new Extractor[List[MsgpackUnion]] {
    override def unapply(value: MsgpackUnion) =
      value.array
    override def apply(value: List[MsgpackUnion]): MsgpackUnion =
      new MsgpackArray(value)
  }
  val vector: Extractor[Vector[MsgpackUnion]] = new Extractor[Vector[MsgpackUnion]] {
    override def unapply(value: MsgpackUnion) =
      value.vector
    override def apply(value: Vector[MsgpackUnion]): MsgpackUnion =
      new MsgpackArray(value.toList)
  }
  val map: Extractor[Map[MsgpackUnion, MsgpackUnion]] = new Extractor[Map[MsgpackUnion, MsgpackUnion]] {
    override def unapply(value: MsgpackUnion) =
      value.map
    override def apply(value: Map[MsgpackUnion, MsgpackUnion]): MsgpackUnion =
      new MsgpackMap(value)
  }
  val imap: Extractor[IMap[MsgpackUnion, MsgpackUnion]] = new Extractor[IMap[MsgpackUnion, MsgpackUnion]] {
    override def unapply(value: MsgpackUnion) =
      value.imap
    override def apply(value: IMap[MsgpackUnion, MsgpackUnion]): MsgpackUnion = {
      val builder = Map.newBuilder[MsgpackUnion, MsgpackUnion]
      value.foldlWithKey(())((_, k, v) => builder += ((k, v)))
      new MsgpackMap(builder.result())
    }
  }
  def extension(tpe: Byte, data: Array[Byte]): MsgpackUnion =
    new MsgpackExt(tpe, data)
  val ext: Extractor[(Byte, Array[Byte])] = new Extractor[(Byte, Array[Byte])] {
    override def unapply(value: MsgpackUnion) =
      value.ext
    override def apply(t: (Byte, Array[Byte])): MsgpackUnion =
      new MsgpackExt(t._1, t._2)
  }
  val bool: Boolean => MsgpackUnion = { value =>
    if (value) MsgpackTrue
    else MsgpackFalse
  }
  val True: MsgpackUnion = MsgpackTrue
  val False: MsgpackUnion = MsgpackFalse
  val nil: MsgpackUnion = MsgpackNil
}

sealed abstract class Extractor[A <: AnyRef] extends (A => MsgpackUnion) {
  def unapply(value: MsgpackUnion): Opt[A]
}

final case class MsgpackString(value: String) extends MsgpackUnion {
  override protected[msgpack4z] def pack(packer: MsgPacker): Unit =
    packer.packString(value)
}

object MsgpackString extends (String => MsgpackUnion)

final case class MsgpackBinary(value: Array[Byte]) extends MsgpackUnion {
  override protected[msgpack4z] def pack(packer: MsgPacker): Unit =
    packer.packBinary(value)
  override def equals(other: Any): Boolean = {
    other match {
      case that: AnyRef if this eq that => true
      case MsgpackBinary(that) =>
        java.util.Arrays.equals(value, that)
      case _ =>
        false
    }
  }
  override def hashCode = java.util.Arrays.hashCode(value)
}

object MsgpackBinary extends (Array[Byte] => MsgpackUnion)

final case class MsgpackLong(value: Long) extends MsgpackUnion {
  override protected[msgpack4z] def pack(packer: MsgPacker): Unit =
    packer.packLong(value)

  override def equals(other: Any): Boolean =
    other match {
      case that: AnyRef if this eq that =>
        true
      case that: MsgpackLong =>
        value == that.value
      case that: MsgpackULong =>
        that.value == bigInteger
      case _ =>
        false
    }

  override def hashCode = BigInteger.valueOf(value).hashCode

  private[msgpack4z] def bigInteger: BigInteger = BigInteger.valueOf(value)
}

object MsgpackLong extends (Long => MsgpackUnion)

final case class MsgpackULong(value: BigInteger) extends MsgpackUnion {
  override protected[msgpack4z] def pack(packer: MsgPacker): Unit =
    packer.packBigInteger(value)

  override def hashCode = value.hashCode

  override def equals(other: Any): Boolean =
    other match {
      case that: AnyRef if this eq that =>
        true
      case that: MsgpackLong =>
        value == that.bigInteger
      case that: MsgpackULong =>
        that.value == value
      case _ =>
        false
    }
}

final case class MsgpackDouble(value: Double) extends MsgpackUnion {
  override protected[msgpack4z] def pack(packer: MsgPacker): Unit =
    packer.packDouble(value)
  override def equals(other: Any): Boolean =
    other match {
      case that: MsgpackDouble =>
        java.lang.Double.doubleToLongBits(this.value) == java.lang.Double.doubleToLongBits(that.value)
      case _ =>
        false
    }
  override final def hashCode = scala.runtime.Statics.doubleHash(value)
}

object MsgpackDouble extends (Double => MsgpackUnion)

final case class MsgpackArray(value: List[MsgpackUnion]) extends MsgpackUnion {
  override protected[msgpack4z] def pack(packer: MsgPacker): Unit = {
    packer.packArrayHeader(value.size)
    var list = value
    while (list ne Nil) {
      list.head.pack(packer)
      list = list.tail
    }
    packer.arrayEnd()
  }
}

final case class MsgpackMap(value: Map[MsgpackUnion, MsgpackUnion]) extends MsgpackUnion {
  override protected[msgpack4z] def pack(packer: MsgPacker): Unit = {
    packer.packMapHeader(value.size)
    value.foreach { case (k, v) =>
      k.pack(packer)
      v.pack(packer)
    }
    packer.mapEnd()
  }
}

object MsgpackMap extends (Map[MsgpackUnion, MsgpackUnion] => MsgpackUnion)

final case class MsgpackExt(tpe: Byte, data: Array[Byte]) extends MsgpackUnion {
  override protected[msgpack4z] def pack(packer: MsgPacker): Unit = {
    packer.packExtTypeHeader(tpe, data.length)
    packer.writePayload(data)
  }

  override def toString: String = hexString("MsgpackExt(type = " + tpe + ", value = ", " ", ")", 4)

  def hexString(start: String, sep: String, end: String, n: Int): String = {
    data.sliding(n, n).map(_.map(x => "%02x".format(x & 0xff)).mkString).mkString(start, sep, end)
  }

  override def hashCode = (tpe * 31) + java.util.Arrays.hashCode(data)

  override def equals(other: Any): Boolean =
    other match {
      case that: MsgpackExt =>
        (this eq that) || (
          (this.tpe == that.tpe) && java.util.Arrays.equals(this.data, that.data)
        )
      case _ =>
        false
    }
}

case object MsgpackTrue extends MsgpackUnion {
  override protected[msgpack4z] def pack(packer: MsgPacker): Unit =
    packer.packBoolean(true)
}

case object MsgpackFalse extends MsgpackUnion {
  override protected[msgpack4z] def pack(packer: MsgPacker): Unit =
    packer.packBoolean(false)
}

case object MsgpackNil extends MsgpackUnion {
  override protected[msgpack4z] def pack(packer: MsgPacker): Unit =
    packer.packNil()
}
