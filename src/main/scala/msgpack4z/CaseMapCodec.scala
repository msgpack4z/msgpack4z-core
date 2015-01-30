package msgpack4z

import scalaz.{NonEmptyList, -\/, \/-}

// GENERATED CODE: DO NOT EDIT.
object CaseMapCodec {

  def string(factory: PackerUnpackerFactory) =
    new CaseMapCodec[String](factory)(CodecInstances.std.stringCodec)

  def int(factory: PackerUnpackerFactory) =
    new CaseMapCodec[Int](factory)(CodecInstances.anyVal.intCodec)
}

class CaseMapCodec[K](factory: PackerUnpackerFactory)(implicit K: MsgpackCodec[K]) {

  private[this] val mapCodec =
    msgpack4z.CodecInstances.std.mapCodec[K, MsgpackUnion]


  def codec2[A1, A2, Z](apply: (A1, A2) => Z, unapply: Z => Option[(A1, A2)])(a1: K, a2: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(2)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2)
        var keys: List[K] = Nil
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply2(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, Z](apply: (A1, A2) => Z, unapply: Z => Option[(A1, A2)])(a1: K, a2: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2]): MsgpackCodec[Z] =
    codec2(apply, unapply)(a1, a2)(A1, A2)

  def codec3[A1, A2, A3, Z](apply: (A1, A2, A3) => Z, unapply: Z => Option[(A1, A2, A3)])(a1: K, a2: K, a3: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(3)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3)
        var keys: List[K] = Nil
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply3(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, Z](apply: (A1, A2, A3) => Z, unapply: Z => Option[(A1, A2, A3)])(a1: K, a2: K, a3: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3]): MsgpackCodec[Z] =
    codec3(apply, unapply)(a1, a2, a3)(A1, A2, A3)

  def codec4[A1, A2, A3, A4, Z](apply: (A1, A2, A3, A4) => Z, unapply: Z => Option[(A1, A2, A3, A4)])(a1: K, a2: K, a3: K, a4: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(4)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4)
        var keys: List[K] = Nil
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply4(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, Z](apply: (A1, A2, A3, A4) => Z, unapply: Z => Option[(A1, A2, A3, A4)])(a1: K, a2: K, a3: K, a4: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4]): MsgpackCodec[Z] =
    codec4(apply, unapply)(a1, a2, a3, a4)(A1, A2, A3, A4)

  def codec5[A1, A2, A3, A4, A5, Z](apply: (A1, A2, A3, A4, A5) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5)])(a1: K, a2: K, a3: K, a4: K, a5: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(5)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5)
        var keys: List[K] = Nil
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply5(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, Z](apply: (A1, A2, A3, A4, A5) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5)])(a1: K, a2: K, a3: K, a4: K, a5: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5]): MsgpackCodec[Z] =
    codec5(apply, unapply)(a1, a2, a3, a4, a5)(A1, A2, A3, A4, A5)

  def codec6[A1, A2, A3, A4, A5, A6, Z](apply: (A1, A2, A3, A4, A5, A6) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(6)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5); K.pack(packer, a6); A6.pack(packer, t._6)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5); val b6 = value.get(a6)
        var keys: List[K] = Nil
        if(b6.isEmpty){ keys = new ::(a6, keys) }
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply6(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5), b6.get.as[A6](factory)(A6)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, A6, Z](apply: (A1, A2, A3, A4, A5, A6) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6]): MsgpackCodec[Z] =
    codec6(apply, unapply)(a1, a2, a3, a4, a5, a6)(A1, A2, A3, A4, A5, A6)

  def codec7[A1, A2, A3, A4, A5, A6, A7, Z](apply: (A1, A2, A3, A4, A5, A6, A7) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(7)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5); K.pack(packer, a6); A6.pack(packer, t._6); K.pack(packer, a7); A7.pack(packer, t._7)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5); val b6 = value.get(a6); val b7 = value.get(a7)
        var keys: List[K] = Nil
        if(b7.isEmpty){ keys = new ::(a7, keys) }
        if(b6.isEmpty){ keys = new ::(a6, keys) }
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply7(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5), b6.get.as[A6](factory)(A6), b7.get.as[A7](factory)(A7)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, A6, A7, Z](apply: (A1, A2, A3, A4, A5, A6, A7) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7]): MsgpackCodec[Z] =
    codec7(apply, unapply)(a1, a2, a3, a4, a5, a6, a7)(A1, A2, A3, A4, A5, A6, A7)

  def codec8[A1, A2, A3, A4, A5, A6, A7, A8, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(8)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5); K.pack(packer, a6); A6.pack(packer, t._6); K.pack(packer, a7); A7.pack(packer, t._7); K.pack(packer, a8); A8.pack(packer, t._8)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5); val b6 = value.get(a6); val b7 = value.get(a7); val b8 = value.get(a8)
        var keys: List[K] = Nil
        if(b8.isEmpty){ keys = new ::(a8, keys) }
        if(b7.isEmpty){ keys = new ::(a7, keys) }
        if(b6.isEmpty){ keys = new ::(a6, keys) }
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply8(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5), b6.get.as[A6](factory)(A6), b7.get.as[A7](factory)(A7), b8.get.as[A8](factory)(A8)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8]): MsgpackCodec[Z] =
    codec8(apply, unapply)(a1, a2, a3, a4, a5, a6, a7, a8)(A1, A2, A3, A4, A5, A6, A7, A8)

  def codec9[A1, A2, A3, A4, A5, A6, A7, A8, A9, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(9)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5); K.pack(packer, a6); A6.pack(packer, t._6); K.pack(packer, a7); A7.pack(packer, t._7); K.pack(packer, a8); A8.pack(packer, t._8); K.pack(packer, a9); A9.pack(packer, t._9)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5); val b6 = value.get(a6); val b7 = value.get(a7); val b8 = value.get(a8); val b9 = value.get(a9)
        var keys: List[K] = Nil
        if(b9.isEmpty){ keys = new ::(a9, keys) }
        if(b8.isEmpty){ keys = new ::(a8, keys) }
        if(b7.isEmpty){ keys = new ::(a7, keys) }
        if(b6.isEmpty){ keys = new ::(a6, keys) }
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply9(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5), b6.get.as[A6](factory)(A6), b7.get.as[A7](factory)(A7), b8.get.as[A8](factory)(A8), b9.get.as[A9](factory)(A9)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9]): MsgpackCodec[Z] =
    codec9(apply, unapply)(a1, a2, a3, a4, a5, a6, a7, a8, a9)(A1, A2, A3, A4, A5, A6, A7, A8, A9)

  def codec10[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(10)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5); K.pack(packer, a6); A6.pack(packer, t._6); K.pack(packer, a7); A7.pack(packer, t._7); K.pack(packer, a8); A8.pack(packer, t._8); K.pack(packer, a9); A9.pack(packer, t._9); K.pack(packer, a10); A10.pack(packer, t._10)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5); val b6 = value.get(a6); val b7 = value.get(a7); val b8 = value.get(a8); val b9 = value.get(a9); val b10 = value.get(a10)
        var keys: List[K] = Nil
        if(b10.isEmpty){ keys = new ::(a10, keys) }
        if(b9.isEmpty){ keys = new ::(a9, keys) }
        if(b8.isEmpty){ keys = new ::(a8, keys) }
        if(b7.isEmpty){ keys = new ::(a7, keys) }
        if(b6.isEmpty){ keys = new ::(a6, keys) }
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply10(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5), b6.get.as[A6](factory)(A6), b7.get.as[A7](factory)(A7), b8.get.as[A8](factory)(A8), b9.get.as[A9](factory)(A9), b10.get.as[A10](factory)(A10)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10]): MsgpackCodec[Z] =
    codec10(apply, unapply)(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10)

  def codec11[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(11)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5); K.pack(packer, a6); A6.pack(packer, t._6); K.pack(packer, a7); A7.pack(packer, t._7); K.pack(packer, a8); A8.pack(packer, t._8); K.pack(packer, a9); A9.pack(packer, t._9); K.pack(packer, a10); A10.pack(packer, t._10); K.pack(packer, a11); A11.pack(packer, t._11)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5); val b6 = value.get(a6); val b7 = value.get(a7); val b8 = value.get(a8); val b9 = value.get(a9); val b10 = value.get(a10); val b11 = value.get(a11)
        var keys: List[K] = Nil
        if(b11.isEmpty){ keys = new ::(a11, keys) }
        if(b10.isEmpty){ keys = new ::(a10, keys) }
        if(b9.isEmpty){ keys = new ::(a9, keys) }
        if(b8.isEmpty){ keys = new ::(a8, keys) }
        if(b7.isEmpty){ keys = new ::(a7, keys) }
        if(b6.isEmpty){ keys = new ::(a6, keys) }
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply11(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5), b6.get.as[A6](factory)(A6), b7.get.as[A7](factory)(A7), b8.get.as[A8](factory)(A8), b9.get.as[A9](factory)(A9), b10.get.as[A10](factory)(A10), b11.get.as[A11](factory)(A11)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11]): MsgpackCodec[Z] =
    codec11(apply, unapply)(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11)

  def codec12[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(12)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5); K.pack(packer, a6); A6.pack(packer, t._6); K.pack(packer, a7); A7.pack(packer, t._7); K.pack(packer, a8); A8.pack(packer, t._8); K.pack(packer, a9); A9.pack(packer, t._9); K.pack(packer, a10); A10.pack(packer, t._10); K.pack(packer, a11); A11.pack(packer, t._11); K.pack(packer, a12); A12.pack(packer, t._12)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5); val b6 = value.get(a6); val b7 = value.get(a7); val b8 = value.get(a8); val b9 = value.get(a9); val b10 = value.get(a10); val b11 = value.get(a11); val b12 = value.get(a12)
        var keys: List[K] = Nil
        if(b12.isEmpty){ keys = new ::(a12, keys) }
        if(b11.isEmpty){ keys = new ::(a11, keys) }
        if(b10.isEmpty){ keys = new ::(a10, keys) }
        if(b9.isEmpty){ keys = new ::(a9, keys) }
        if(b8.isEmpty){ keys = new ::(a8, keys) }
        if(b7.isEmpty){ keys = new ::(a7, keys) }
        if(b6.isEmpty){ keys = new ::(a6, keys) }
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply12(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5), b6.get.as[A6](factory)(A6), b7.get.as[A7](factory)(A7), b8.get.as[A8](factory)(A8), b9.get.as[A9](factory)(A9), b10.get.as[A10](factory)(A10), b11.get.as[A11](factory)(A11), b12.get.as[A12](factory)(A12)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12]): MsgpackCodec[Z] =
    codec12(apply, unapply)(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12)

  def codec13[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(13)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5); K.pack(packer, a6); A6.pack(packer, t._6); K.pack(packer, a7); A7.pack(packer, t._7); K.pack(packer, a8); A8.pack(packer, t._8); K.pack(packer, a9); A9.pack(packer, t._9); K.pack(packer, a10); A10.pack(packer, t._10); K.pack(packer, a11); A11.pack(packer, t._11); K.pack(packer, a12); A12.pack(packer, t._12); K.pack(packer, a13); A13.pack(packer, t._13)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5); val b6 = value.get(a6); val b7 = value.get(a7); val b8 = value.get(a8); val b9 = value.get(a9); val b10 = value.get(a10); val b11 = value.get(a11); val b12 = value.get(a12); val b13 = value.get(a13)
        var keys: List[K] = Nil
        if(b13.isEmpty){ keys = new ::(a13, keys) }
        if(b12.isEmpty){ keys = new ::(a12, keys) }
        if(b11.isEmpty){ keys = new ::(a11, keys) }
        if(b10.isEmpty){ keys = new ::(a10, keys) }
        if(b9.isEmpty){ keys = new ::(a9, keys) }
        if(b8.isEmpty){ keys = new ::(a8, keys) }
        if(b7.isEmpty){ keys = new ::(a7, keys) }
        if(b6.isEmpty){ keys = new ::(a6, keys) }
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply13(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5), b6.get.as[A6](factory)(A6), b7.get.as[A7](factory)(A7), b8.get.as[A8](factory)(A8), b9.get.as[A9](factory)(A9), b10.get.as[A10](factory)(A10), b11.get.as[A11](factory)(A11), b12.get.as[A12](factory)(A12), b13.get.as[A13](factory)(A13)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13]): MsgpackCodec[Z] =
    codec13(apply, unapply)(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13)

  def codec14[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(14)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5); K.pack(packer, a6); A6.pack(packer, t._6); K.pack(packer, a7); A7.pack(packer, t._7); K.pack(packer, a8); A8.pack(packer, t._8); K.pack(packer, a9); A9.pack(packer, t._9); K.pack(packer, a10); A10.pack(packer, t._10); K.pack(packer, a11); A11.pack(packer, t._11); K.pack(packer, a12); A12.pack(packer, t._12); K.pack(packer, a13); A13.pack(packer, t._13); K.pack(packer, a14); A14.pack(packer, t._14)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5); val b6 = value.get(a6); val b7 = value.get(a7); val b8 = value.get(a8); val b9 = value.get(a9); val b10 = value.get(a10); val b11 = value.get(a11); val b12 = value.get(a12); val b13 = value.get(a13); val b14 = value.get(a14)
        var keys: List[K] = Nil
        if(b14.isEmpty){ keys = new ::(a14, keys) }
        if(b13.isEmpty){ keys = new ::(a13, keys) }
        if(b12.isEmpty){ keys = new ::(a12, keys) }
        if(b11.isEmpty){ keys = new ::(a11, keys) }
        if(b10.isEmpty){ keys = new ::(a10, keys) }
        if(b9.isEmpty){ keys = new ::(a9, keys) }
        if(b8.isEmpty){ keys = new ::(a8, keys) }
        if(b7.isEmpty){ keys = new ::(a7, keys) }
        if(b6.isEmpty){ keys = new ::(a6, keys) }
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply14(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5), b6.get.as[A6](factory)(A6), b7.get.as[A7](factory)(A7), b8.get.as[A8](factory)(A8), b9.get.as[A9](factory)(A9), b10.get.as[A10](factory)(A10), b11.get.as[A11](factory)(A11), b12.get.as[A12](factory)(A12), b13.get.as[A13](factory)(A13), b14.get.as[A14](factory)(A14)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14]): MsgpackCodec[Z] =
    codec14(apply, unapply)(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14)

  def codec15[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K, a15: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(15)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5); K.pack(packer, a6); A6.pack(packer, t._6); K.pack(packer, a7); A7.pack(packer, t._7); K.pack(packer, a8); A8.pack(packer, t._8); K.pack(packer, a9); A9.pack(packer, t._9); K.pack(packer, a10); A10.pack(packer, t._10); K.pack(packer, a11); A11.pack(packer, t._11); K.pack(packer, a12); A12.pack(packer, t._12); K.pack(packer, a13); A13.pack(packer, t._13); K.pack(packer, a14); A14.pack(packer, t._14); K.pack(packer, a15); A15.pack(packer, t._15)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5); val b6 = value.get(a6); val b7 = value.get(a7); val b8 = value.get(a8); val b9 = value.get(a9); val b10 = value.get(a10); val b11 = value.get(a11); val b12 = value.get(a12); val b13 = value.get(a13); val b14 = value.get(a14); val b15 = value.get(a15)
        var keys: List[K] = Nil
        if(b15.isEmpty){ keys = new ::(a15, keys) }
        if(b14.isEmpty){ keys = new ::(a14, keys) }
        if(b13.isEmpty){ keys = new ::(a13, keys) }
        if(b12.isEmpty){ keys = new ::(a12, keys) }
        if(b11.isEmpty){ keys = new ::(a11, keys) }
        if(b10.isEmpty){ keys = new ::(a10, keys) }
        if(b9.isEmpty){ keys = new ::(a9, keys) }
        if(b8.isEmpty){ keys = new ::(a8, keys) }
        if(b7.isEmpty){ keys = new ::(a7, keys) }
        if(b6.isEmpty){ keys = new ::(a6, keys) }
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply15(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5), b6.get.as[A6](factory)(A6), b7.get.as[A7](factory)(A7), b8.get.as[A8](factory)(A8), b9.get.as[A9](factory)(A9), b10.get.as[A10](factory)(A10), b11.get.as[A11](factory)(A11), b12.get.as[A12](factory)(A12), b13.get.as[A13](factory)(A13), b14.get.as[A14](factory)(A14), b15.get.as[A15](factory)(A15)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K, a15: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15]): MsgpackCodec[Z] =
    codec15(apply, unapply)(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15)

  def codec16[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K, a15: K, a16: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(16)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5); K.pack(packer, a6); A6.pack(packer, t._6); K.pack(packer, a7); A7.pack(packer, t._7); K.pack(packer, a8); A8.pack(packer, t._8); K.pack(packer, a9); A9.pack(packer, t._9); K.pack(packer, a10); A10.pack(packer, t._10); K.pack(packer, a11); A11.pack(packer, t._11); K.pack(packer, a12); A12.pack(packer, t._12); K.pack(packer, a13); A13.pack(packer, t._13); K.pack(packer, a14); A14.pack(packer, t._14); K.pack(packer, a15); A15.pack(packer, t._15); K.pack(packer, a16); A16.pack(packer, t._16)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5); val b6 = value.get(a6); val b7 = value.get(a7); val b8 = value.get(a8); val b9 = value.get(a9); val b10 = value.get(a10); val b11 = value.get(a11); val b12 = value.get(a12); val b13 = value.get(a13); val b14 = value.get(a14); val b15 = value.get(a15); val b16 = value.get(a16)
        var keys: List[K] = Nil
        if(b16.isEmpty){ keys = new ::(a16, keys) }
        if(b15.isEmpty){ keys = new ::(a15, keys) }
        if(b14.isEmpty){ keys = new ::(a14, keys) }
        if(b13.isEmpty){ keys = new ::(a13, keys) }
        if(b12.isEmpty){ keys = new ::(a12, keys) }
        if(b11.isEmpty){ keys = new ::(a11, keys) }
        if(b10.isEmpty){ keys = new ::(a10, keys) }
        if(b9.isEmpty){ keys = new ::(a9, keys) }
        if(b8.isEmpty){ keys = new ::(a8, keys) }
        if(b7.isEmpty){ keys = new ::(a7, keys) }
        if(b6.isEmpty){ keys = new ::(a6, keys) }
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply16(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5), b6.get.as[A6](factory)(A6), b7.get.as[A7](factory)(A7), b8.get.as[A8](factory)(A8), b9.get.as[A9](factory)(A9), b10.get.as[A10](factory)(A10), b11.get.as[A11](factory)(A11), b12.get.as[A12](factory)(A12), b13.get.as[A13](factory)(A13), b14.get.as[A14](factory)(A14), b15.get.as[A15](factory)(A15), b16.get.as[A16](factory)(A16)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K, a15: K, a16: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16]): MsgpackCodec[Z] =
    codec16(apply, unapply)(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16)

  def codec17[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K, a15: K, a16: K, a17: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(17)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5); K.pack(packer, a6); A6.pack(packer, t._6); K.pack(packer, a7); A7.pack(packer, t._7); K.pack(packer, a8); A8.pack(packer, t._8); K.pack(packer, a9); A9.pack(packer, t._9); K.pack(packer, a10); A10.pack(packer, t._10); K.pack(packer, a11); A11.pack(packer, t._11); K.pack(packer, a12); A12.pack(packer, t._12); K.pack(packer, a13); A13.pack(packer, t._13); K.pack(packer, a14); A14.pack(packer, t._14); K.pack(packer, a15); A15.pack(packer, t._15); K.pack(packer, a16); A16.pack(packer, t._16); K.pack(packer, a17); A17.pack(packer, t._17)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5); val b6 = value.get(a6); val b7 = value.get(a7); val b8 = value.get(a8); val b9 = value.get(a9); val b10 = value.get(a10); val b11 = value.get(a11); val b12 = value.get(a12); val b13 = value.get(a13); val b14 = value.get(a14); val b15 = value.get(a15); val b16 = value.get(a16); val b17 = value.get(a17)
        var keys: List[K] = Nil
        if(b17.isEmpty){ keys = new ::(a17, keys) }
        if(b16.isEmpty){ keys = new ::(a16, keys) }
        if(b15.isEmpty){ keys = new ::(a15, keys) }
        if(b14.isEmpty){ keys = new ::(a14, keys) }
        if(b13.isEmpty){ keys = new ::(a13, keys) }
        if(b12.isEmpty){ keys = new ::(a12, keys) }
        if(b11.isEmpty){ keys = new ::(a11, keys) }
        if(b10.isEmpty){ keys = new ::(a10, keys) }
        if(b9.isEmpty){ keys = new ::(a9, keys) }
        if(b8.isEmpty){ keys = new ::(a8, keys) }
        if(b7.isEmpty){ keys = new ::(a7, keys) }
        if(b6.isEmpty){ keys = new ::(a6, keys) }
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply17(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5), b6.get.as[A6](factory)(A6), b7.get.as[A7](factory)(A7), b8.get.as[A8](factory)(A8), b9.get.as[A9](factory)(A9), b10.get.as[A10](factory)(A10), b11.get.as[A11](factory)(A11), b12.get.as[A12](factory)(A12), b13.get.as[A13](factory)(A13), b14.get.as[A14](factory)(A14), b15.get.as[A15](factory)(A15), b16.get.as[A16](factory)(A16), b17.get.as[A17](factory)(A17)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K, a15: K, a16: K, a17: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17]): MsgpackCodec[Z] =
    codec17(apply, unapply)(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17)

  def codec18[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K, a15: K, a16: K, a17: K, a18: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(18)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5); K.pack(packer, a6); A6.pack(packer, t._6); K.pack(packer, a7); A7.pack(packer, t._7); K.pack(packer, a8); A8.pack(packer, t._8); K.pack(packer, a9); A9.pack(packer, t._9); K.pack(packer, a10); A10.pack(packer, t._10); K.pack(packer, a11); A11.pack(packer, t._11); K.pack(packer, a12); A12.pack(packer, t._12); K.pack(packer, a13); A13.pack(packer, t._13); K.pack(packer, a14); A14.pack(packer, t._14); K.pack(packer, a15); A15.pack(packer, t._15); K.pack(packer, a16); A16.pack(packer, t._16); K.pack(packer, a17); A17.pack(packer, t._17); K.pack(packer, a18); A18.pack(packer, t._18)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5); val b6 = value.get(a6); val b7 = value.get(a7); val b8 = value.get(a8); val b9 = value.get(a9); val b10 = value.get(a10); val b11 = value.get(a11); val b12 = value.get(a12); val b13 = value.get(a13); val b14 = value.get(a14); val b15 = value.get(a15); val b16 = value.get(a16); val b17 = value.get(a17); val b18 = value.get(a18)
        var keys: List[K] = Nil
        if(b18.isEmpty){ keys = new ::(a18, keys) }
        if(b17.isEmpty){ keys = new ::(a17, keys) }
        if(b16.isEmpty){ keys = new ::(a16, keys) }
        if(b15.isEmpty){ keys = new ::(a15, keys) }
        if(b14.isEmpty){ keys = new ::(a14, keys) }
        if(b13.isEmpty){ keys = new ::(a13, keys) }
        if(b12.isEmpty){ keys = new ::(a12, keys) }
        if(b11.isEmpty){ keys = new ::(a11, keys) }
        if(b10.isEmpty){ keys = new ::(a10, keys) }
        if(b9.isEmpty){ keys = new ::(a9, keys) }
        if(b8.isEmpty){ keys = new ::(a8, keys) }
        if(b7.isEmpty){ keys = new ::(a7, keys) }
        if(b6.isEmpty){ keys = new ::(a6, keys) }
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply18(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5), b6.get.as[A6](factory)(A6), b7.get.as[A7](factory)(A7), b8.get.as[A8](factory)(A8), b9.get.as[A9](factory)(A9), b10.get.as[A10](factory)(A10), b11.get.as[A11](factory)(A11), b12.get.as[A12](factory)(A12), b13.get.as[A13](factory)(A13), b14.get.as[A14](factory)(A14), b15.get.as[A15](factory)(A15), b16.get.as[A16](factory)(A16), b17.get.as[A17](factory)(A17), b18.get.as[A18](factory)(A18)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K, a15: K, a16: K, a17: K, a18: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18]): MsgpackCodec[Z] =
    codec18(apply, unapply)(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18)

  def codec19[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K, a15: K, a16: K, a17: K, a18: K, a19: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18], A19: MsgpackCodec[A19]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(19)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5); K.pack(packer, a6); A6.pack(packer, t._6); K.pack(packer, a7); A7.pack(packer, t._7); K.pack(packer, a8); A8.pack(packer, t._8); K.pack(packer, a9); A9.pack(packer, t._9); K.pack(packer, a10); A10.pack(packer, t._10); K.pack(packer, a11); A11.pack(packer, t._11); K.pack(packer, a12); A12.pack(packer, t._12); K.pack(packer, a13); A13.pack(packer, t._13); K.pack(packer, a14); A14.pack(packer, t._14); K.pack(packer, a15); A15.pack(packer, t._15); K.pack(packer, a16); A16.pack(packer, t._16); K.pack(packer, a17); A17.pack(packer, t._17); K.pack(packer, a18); A18.pack(packer, t._18); K.pack(packer, a19); A19.pack(packer, t._19)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5); val b6 = value.get(a6); val b7 = value.get(a7); val b8 = value.get(a8); val b9 = value.get(a9); val b10 = value.get(a10); val b11 = value.get(a11); val b12 = value.get(a12); val b13 = value.get(a13); val b14 = value.get(a14); val b15 = value.get(a15); val b16 = value.get(a16); val b17 = value.get(a17); val b18 = value.get(a18); val b19 = value.get(a19)
        var keys: List[K] = Nil
        if(b19.isEmpty){ keys = new ::(a19, keys) }
        if(b18.isEmpty){ keys = new ::(a18, keys) }
        if(b17.isEmpty){ keys = new ::(a17, keys) }
        if(b16.isEmpty){ keys = new ::(a16, keys) }
        if(b15.isEmpty){ keys = new ::(a15, keys) }
        if(b14.isEmpty){ keys = new ::(a14, keys) }
        if(b13.isEmpty){ keys = new ::(a13, keys) }
        if(b12.isEmpty){ keys = new ::(a12, keys) }
        if(b11.isEmpty){ keys = new ::(a11, keys) }
        if(b10.isEmpty){ keys = new ::(a10, keys) }
        if(b9.isEmpty){ keys = new ::(a9, keys) }
        if(b8.isEmpty){ keys = new ::(a8, keys) }
        if(b7.isEmpty){ keys = new ::(a7, keys) }
        if(b6.isEmpty){ keys = new ::(a6, keys) }
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply19(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5), b6.get.as[A6](factory)(A6), b7.get.as[A7](factory)(A7), b8.get.as[A8](factory)(A8), b9.get.as[A9](factory)(A9), b10.get.as[A10](factory)(A10), b11.get.as[A11](factory)(A11), b12.get.as[A12](factory)(A12), b13.get.as[A13](factory)(A13), b14.get.as[A14](factory)(A14), b15.get.as[A15](factory)(A15), b16.get.as[A16](factory)(A16), b17.get.as[A17](factory)(A17), b18.get.as[A18](factory)(A18), b19.get.as[A19](factory)(A19)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K, a15: K, a16: K, a17: K, a18: K, a19: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18], A19: MsgpackCodec[A19]): MsgpackCodec[Z] =
    codec19(apply, unapply)(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19)

  def codec20[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K, a15: K, a16: K, a17: K, a18: K, a19: K, a20: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18], A19: MsgpackCodec[A19], A20: MsgpackCodec[A20]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(20)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5); K.pack(packer, a6); A6.pack(packer, t._6); K.pack(packer, a7); A7.pack(packer, t._7); K.pack(packer, a8); A8.pack(packer, t._8); K.pack(packer, a9); A9.pack(packer, t._9); K.pack(packer, a10); A10.pack(packer, t._10); K.pack(packer, a11); A11.pack(packer, t._11); K.pack(packer, a12); A12.pack(packer, t._12); K.pack(packer, a13); A13.pack(packer, t._13); K.pack(packer, a14); A14.pack(packer, t._14); K.pack(packer, a15); A15.pack(packer, t._15); K.pack(packer, a16); A16.pack(packer, t._16); K.pack(packer, a17); A17.pack(packer, t._17); K.pack(packer, a18); A18.pack(packer, t._18); K.pack(packer, a19); A19.pack(packer, t._19); K.pack(packer, a20); A20.pack(packer, t._20)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5); val b6 = value.get(a6); val b7 = value.get(a7); val b8 = value.get(a8); val b9 = value.get(a9); val b10 = value.get(a10); val b11 = value.get(a11); val b12 = value.get(a12); val b13 = value.get(a13); val b14 = value.get(a14); val b15 = value.get(a15); val b16 = value.get(a16); val b17 = value.get(a17); val b18 = value.get(a18); val b19 = value.get(a19); val b20 = value.get(a20)
        var keys: List[K] = Nil
        if(b20.isEmpty){ keys = new ::(a20, keys) }
        if(b19.isEmpty){ keys = new ::(a19, keys) }
        if(b18.isEmpty){ keys = new ::(a18, keys) }
        if(b17.isEmpty){ keys = new ::(a17, keys) }
        if(b16.isEmpty){ keys = new ::(a16, keys) }
        if(b15.isEmpty){ keys = new ::(a15, keys) }
        if(b14.isEmpty){ keys = new ::(a14, keys) }
        if(b13.isEmpty){ keys = new ::(a13, keys) }
        if(b12.isEmpty){ keys = new ::(a12, keys) }
        if(b11.isEmpty){ keys = new ::(a11, keys) }
        if(b10.isEmpty){ keys = new ::(a10, keys) }
        if(b9.isEmpty){ keys = new ::(a9, keys) }
        if(b8.isEmpty){ keys = new ::(a8, keys) }
        if(b7.isEmpty){ keys = new ::(a7, keys) }
        if(b6.isEmpty){ keys = new ::(a6, keys) }
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply20(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5), b6.get.as[A6](factory)(A6), b7.get.as[A7](factory)(A7), b8.get.as[A8](factory)(A8), b9.get.as[A9](factory)(A9), b10.get.as[A10](factory)(A10), b11.get.as[A11](factory)(A11), b12.get.as[A12](factory)(A12), b13.get.as[A13](factory)(A13), b14.get.as[A14](factory)(A14), b15.get.as[A15](factory)(A15), b16.get.as[A16](factory)(A16), b17.get.as[A17](factory)(A17), b18.get.as[A18](factory)(A18), b19.get.as[A19](factory)(A19), b20.get.as[A20](factory)(A20)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K, a15: K, a16: K, a17: K, a18: K, a19: K, a20: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18], A19: MsgpackCodec[A19], A20: MsgpackCodec[A20]): MsgpackCodec[Z] =
    codec20(apply, unapply)(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20)

  def codec21[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K, a15: K, a16: K, a17: K, a18: K, a19: K, a20: K, a21: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18], A19: MsgpackCodec[A19], A20: MsgpackCodec[A20], A21: MsgpackCodec[A21]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(21)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5); K.pack(packer, a6); A6.pack(packer, t._6); K.pack(packer, a7); A7.pack(packer, t._7); K.pack(packer, a8); A8.pack(packer, t._8); K.pack(packer, a9); A9.pack(packer, t._9); K.pack(packer, a10); A10.pack(packer, t._10); K.pack(packer, a11); A11.pack(packer, t._11); K.pack(packer, a12); A12.pack(packer, t._12); K.pack(packer, a13); A13.pack(packer, t._13); K.pack(packer, a14); A14.pack(packer, t._14); K.pack(packer, a15); A15.pack(packer, t._15); K.pack(packer, a16); A16.pack(packer, t._16); K.pack(packer, a17); A17.pack(packer, t._17); K.pack(packer, a18); A18.pack(packer, t._18); K.pack(packer, a19); A19.pack(packer, t._19); K.pack(packer, a20); A20.pack(packer, t._20); K.pack(packer, a21); A21.pack(packer, t._21)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5); val b6 = value.get(a6); val b7 = value.get(a7); val b8 = value.get(a8); val b9 = value.get(a9); val b10 = value.get(a10); val b11 = value.get(a11); val b12 = value.get(a12); val b13 = value.get(a13); val b14 = value.get(a14); val b15 = value.get(a15); val b16 = value.get(a16); val b17 = value.get(a17); val b18 = value.get(a18); val b19 = value.get(a19); val b20 = value.get(a20); val b21 = value.get(a21)
        var keys: List[K] = Nil
        if(b21.isEmpty){ keys = new ::(a21, keys) }
        if(b20.isEmpty){ keys = new ::(a20, keys) }
        if(b19.isEmpty){ keys = new ::(a19, keys) }
        if(b18.isEmpty){ keys = new ::(a18, keys) }
        if(b17.isEmpty){ keys = new ::(a17, keys) }
        if(b16.isEmpty){ keys = new ::(a16, keys) }
        if(b15.isEmpty){ keys = new ::(a15, keys) }
        if(b14.isEmpty){ keys = new ::(a14, keys) }
        if(b13.isEmpty){ keys = new ::(a13, keys) }
        if(b12.isEmpty){ keys = new ::(a12, keys) }
        if(b11.isEmpty){ keys = new ::(a11, keys) }
        if(b10.isEmpty){ keys = new ::(a10, keys) }
        if(b9.isEmpty){ keys = new ::(a9, keys) }
        if(b8.isEmpty){ keys = new ::(a8, keys) }
        if(b7.isEmpty){ keys = new ::(a7, keys) }
        if(b6.isEmpty){ keys = new ::(a6, keys) }
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply21(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5), b6.get.as[A6](factory)(A6), b7.get.as[A7](factory)(A7), b8.get.as[A8](factory)(A8), b9.get.as[A9](factory)(A9), b10.get.as[A10](factory)(A10), b11.get.as[A11](factory)(A11), b12.get.as[A12](factory)(A12), b13.get.as[A13](factory)(A13), b14.get.as[A14](factory)(A14), b15.get.as[A15](factory)(A15), b16.get.as[A16](factory)(A16), b17.get.as[A17](factory)(A17), b18.get.as[A18](factory)(A18), b19.get.as[A19](factory)(A19), b20.get.as[A20](factory)(A20), b21.get.as[A21](factory)(A21)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K, a15: K, a16: K, a17: K, a18: K, a19: K, a20: K, a21: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18], A19: MsgpackCodec[A19], A20: MsgpackCodec[A20], A21: MsgpackCodec[A21]): MsgpackCodec[Z] =
    codec21(apply, unapply)(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21)

  def codec22[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K, a15: K, a16: K, a17: K, a18: K, a19: K, a20: K, a21: K, a22: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18], A19: MsgpackCodec[A19], A20: MsgpackCodec[A20], A21: MsgpackCodec[A21], A22: MsgpackCodec[A22]): MsgpackCodec[Z] =
    MsgpackCodec.codec[Z](
    (packer, value) => {
      packer.packMapHeader(22)
      val t = unapply(value).get
      K.pack(packer, a1); A1.pack(packer, t._1); K.pack(packer, a2); A2.pack(packer, t._2); K.pack(packer, a3); A3.pack(packer, t._3); K.pack(packer, a4); A4.pack(packer, t._4); K.pack(packer, a5); A5.pack(packer, t._5); K.pack(packer, a6); A6.pack(packer, t._6); K.pack(packer, a7); A7.pack(packer, t._7); K.pack(packer, a8); A8.pack(packer, t._8); K.pack(packer, a9); A9.pack(packer, t._9); K.pack(packer, a10); A10.pack(packer, t._10); K.pack(packer, a11); A11.pack(packer, t._11); K.pack(packer, a12); A12.pack(packer, t._12); K.pack(packer, a13); A13.pack(packer, t._13); K.pack(packer, a14); A14.pack(packer, t._14); K.pack(packer, a15); A15.pack(packer, t._15); K.pack(packer, a16); A16.pack(packer, t._16); K.pack(packer, a17); A17.pack(packer, t._17); K.pack(packer, a18); A18.pack(packer, t._18); K.pack(packer, a19); A19.pack(packer, t._19); K.pack(packer, a20); A20.pack(packer, t._20); K.pack(packer, a21); A21.pack(packer, t._21); K.pack(packer, a22); A22.pack(packer, t._22)
      packer.mapEnd()
    },
    unpacker => mapCodec.unpack(unpacker) match {
      case \/-(value) =>
        val b1 = value.get(a1); val b2 = value.get(a2); val b3 = value.get(a3); val b4 = value.get(a4); val b5 = value.get(a5); val b6 = value.get(a6); val b7 = value.get(a7); val b8 = value.get(a8); val b9 = value.get(a9); val b10 = value.get(a10); val b11 = value.get(a11); val b12 = value.get(a12); val b13 = value.get(a13); val b14 = value.get(a14); val b15 = value.get(a15); val b16 = value.get(a16); val b17 = value.get(a17); val b18 = value.get(a18); val b19 = value.get(a19); val b20 = value.get(a20); val b21 = value.get(a21); val b22 = value.get(a22)
        var keys: List[K] = Nil
        if(b22.isEmpty){ keys = new ::(a22, keys) }
        if(b21.isEmpty){ keys = new ::(a21, keys) }
        if(b20.isEmpty){ keys = new ::(a20, keys) }
        if(b19.isEmpty){ keys = new ::(a19, keys) }
        if(b18.isEmpty){ keys = new ::(a18, keys) }
        if(b17.isEmpty){ keys = new ::(a17, keys) }
        if(b16.isEmpty){ keys = new ::(a16, keys) }
        if(b15.isEmpty){ keys = new ::(a15, keys) }
        if(b14.isEmpty){ keys = new ::(a14, keys) }
        if(b13.isEmpty){ keys = new ::(a13, keys) }
        if(b12.isEmpty){ keys = new ::(a12, keys) }
        if(b11.isEmpty){ keys = new ::(a11, keys) }
        if(b10.isEmpty){ keys = new ::(a10, keys) }
        if(b9.isEmpty){ keys = new ::(a9, keys) }
        if(b8.isEmpty){ keys = new ::(a8, keys) }
        if(b7.isEmpty){ keys = new ::(a7, keys) }
        if(b6.isEmpty){ keys = new ::(a6, keys) }
        if(b5.isEmpty){ keys = new ::(a5, keys) }
        if(b4.isEmpty){ keys = new ::(a4, keys) }
        if(b3.isEmpty){ keys = new ::(a3, keys) }
        if(b2.isEmpty){ keys = new ::(a2, keys) }
        if(b1.isEmpty){ keys = new ::(a1, keys) }

        keys match {
          case h :: t =>
            -\/(Err(CaseClassMapMissingKeyError(NonEmptyList.nel(h, t), K)))
          case _ =>
            zeroapply.DisjunctionApply.apply22(
              b1.get.as[A1](factory)(A1), b2.get.as[A2](factory)(A2), b3.get.as[A3](factory)(A3), b4.get.as[A4](factory)(A4), b5.get.as[A5](factory)(A5), b6.get.as[A6](factory)(A6), b7.get.as[A7](factory)(A7), b8.get.as[A8](factory)(A8), b9.get.as[A9](factory)(A9), b10.get.as[A10](factory)(A10), b11.get.as[A11](factory)(A11), b12.get.as[A12](factory)(A12), b13.get.as[A13](factory)(A13), b14.get.as[A14](factory)(A14), b15.get.as[A15](factory)(A15), b16.get.as[A16](factory)(A16), b17.get.as[A17](factory)(A17), b18.get.as[A18](factory)(A18), b19.get.as[A19](factory)(A19), b20.get.as[A20](factory)(A20), b21.get.as[A21](factory)(A21), b22.get.as[A22](factory)(A22)
            )(apply)
        }
      case e @ -\/(_) => e
    }
  )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, Z](apply: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22) => Z, unapply: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22)])(a1: K, a2: K, a3: K, a4: K, a5: K, a6: K, a7: K, a8: K, a9: K, a10: K, a11: K, a12: K, a13: K, a14: K, a15: K, a16: K, a17: K, a18: K, a19: K, a20: K, a21: K, a22: K)(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18], A19: MsgpackCodec[A19], A20: MsgpackCodec[A20], A21: MsgpackCodec[A21], A22: MsgpackCodec[A22]): MsgpackCodec[Z] =
    codec22(apply, unapply)(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22)
}
