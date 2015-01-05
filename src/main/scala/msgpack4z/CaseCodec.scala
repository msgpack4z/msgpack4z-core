package msgpack4z

import scalaz.-\/

// GENERATED CODE: DO NOT EDIT.
object CaseCodec {

  def codec1[A1, Z](construct: A1 => Z, extract: Z => Option[A1])(implicit A1: MsgpackCodec[A1]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(1)
        A1.pack(packer, extract(z).get)
        packer.arrayEnd()
      }
      ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 1) {
          A1.unpack(unpacker).map(construct)
        }else{
          -\/(new UnexpectedArraySize(1, size))
        }
      }
    )

  def codec[A1, Z](construct: A1 => Z, extract: Z => Option[A1])(implicit A1: MsgpackCodec[A1]): MsgpackCodec[Z] =
    codec1(construct, extract)(A1)



  def codec2[A1, A2, Z](construct: (A1, A2) => Z, extract: Z => Option[(A1, A2)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(2)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 2) {
          val result = zeroapply.DisjunctionApply.apply2(
            A1.unpack(unpacker), A2.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(2, size))
        }
      }
    )

  def codec[A1, A2, Z](construct: (A1, A2) => Z, extract: Z => Option[(A1, A2)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2]): MsgpackCodec[Z] =
    codec2(construct, extract)(A1, A2)


  def codec3[A1, A2, A3, Z](construct: (A1, A2, A3) => Z, extract: Z => Option[(A1, A2, A3)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(3)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 3) {
          val result = zeroapply.DisjunctionApply.apply3(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(3, size))
        }
      }
    )

  def codec[A1, A2, A3, Z](construct: (A1, A2, A3) => Z, extract: Z => Option[(A1, A2, A3)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3]): MsgpackCodec[Z] =
    codec3(construct, extract)(A1, A2, A3)


  def codec4[A1, A2, A3, A4, Z](construct: (A1, A2, A3, A4) => Z, extract: Z => Option[(A1, A2, A3, A4)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(4)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 4) {
          val result = zeroapply.DisjunctionApply.apply4(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(4, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, Z](construct: (A1, A2, A3, A4) => Z, extract: Z => Option[(A1, A2, A3, A4)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4]): MsgpackCodec[Z] =
    codec4(construct, extract)(A1, A2, A3, A4)


  def codec5[A1, A2, A3, A4, A5, Z](construct: (A1, A2, A3, A4, A5) => Z, extract: Z => Option[(A1, A2, A3, A4, A5)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(5)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 5) {
          val result = zeroapply.DisjunctionApply.apply5(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(5, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, Z](construct: (A1, A2, A3, A4, A5) => Z, extract: Z => Option[(A1, A2, A3, A4, A5)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5]): MsgpackCodec[Z] =
    codec5(construct, extract)(A1, A2, A3, A4, A5)


  def codec6[A1, A2, A3, A4, A5, A6, Z](construct: (A1, A2, A3, A4, A5, A6) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(6)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5); A6.pack(packer, x._6)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 6) {
          val result = zeroapply.DisjunctionApply.apply6(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker), A6.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(6, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, A6, Z](construct: (A1, A2, A3, A4, A5, A6) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6]): MsgpackCodec[Z] =
    codec6(construct, extract)(A1, A2, A3, A4, A5, A6)


  def codec7[A1, A2, A3, A4, A5, A6, A7, Z](construct: (A1, A2, A3, A4, A5, A6, A7) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(7)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5); A6.pack(packer, x._6); A7.pack(packer, x._7)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 7) {
          val result = zeroapply.DisjunctionApply.apply7(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker), A6.unpack(unpacker), A7.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(7, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, A6, A7, Z](construct: (A1, A2, A3, A4, A5, A6, A7) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7]): MsgpackCodec[Z] =
    codec7(construct, extract)(A1, A2, A3, A4, A5, A6, A7)


  def codec8[A1, A2, A3, A4, A5, A6, A7, A8, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(8)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5); A6.pack(packer, x._6); A7.pack(packer, x._7); A8.pack(packer, x._8)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 8) {
          val result = zeroapply.DisjunctionApply.apply8(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker), A6.unpack(unpacker), A7.unpack(unpacker), A8.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(8, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8]): MsgpackCodec[Z] =
    codec8(construct, extract)(A1, A2, A3, A4, A5, A6, A7, A8)


  def codec9[A1, A2, A3, A4, A5, A6, A7, A8, A9, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(9)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5); A6.pack(packer, x._6); A7.pack(packer, x._7); A8.pack(packer, x._8); A9.pack(packer, x._9)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 9) {
          val result = zeroapply.DisjunctionApply.apply9(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker), A6.unpack(unpacker), A7.unpack(unpacker), A8.unpack(unpacker), A9.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(9, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9]): MsgpackCodec[Z] =
    codec9(construct, extract)(A1, A2, A3, A4, A5, A6, A7, A8, A9)


  def codec10[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(10)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5); A6.pack(packer, x._6); A7.pack(packer, x._7); A8.pack(packer, x._8); A9.pack(packer, x._9); A10.pack(packer, x._10)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 10) {
          val result = zeroapply.DisjunctionApply.apply10(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker), A6.unpack(unpacker), A7.unpack(unpacker), A8.unpack(unpacker), A9.unpack(unpacker), A10.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(10, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10]): MsgpackCodec[Z] =
    codec10(construct, extract)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10)


  def codec11[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(11)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5); A6.pack(packer, x._6); A7.pack(packer, x._7); A8.pack(packer, x._8); A9.pack(packer, x._9); A10.pack(packer, x._10); A11.pack(packer, x._11)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 11) {
          val result = zeroapply.DisjunctionApply.apply11(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker), A6.unpack(unpacker), A7.unpack(unpacker), A8.unpack(unpacker), A9.unpack(unpacker), A10.unpack(unpacker), A11.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(11, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11]): MsgpackCodec[Z] =
    codec11(construct, extract)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11)


  def codec12[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(12)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5); A6.pack(packer, x._6); A7.pack(packer, x._7); A8.pack(packer, x._8); A9.pack(packer, x._9); A10.pack(packer, x._10); A11.pack(packer, x._11); A12.pack(packer, x._12)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 12) {
          val result = zeroapply.DisjunctionApply.apply12(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker), A6.unpack(unpacker), A7.unpack(unpacker), A8.unpack(unpacker), A9.unpack(unpacker), A10.unpack(unpacker), A11.unpack(unpacker), A12.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(12, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12]): MsgpackCodec[Z] =
    codec12(construct, extract)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12)


  def codec13[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(13)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5); A6.pack(packer, x._6); A7.pack(packer, x._7); A8.pack(packer, x._8); A9.pack(packer, x._9); A10.pack(packer, x._10); A11.pack(packer, x._11); A12.pack(packer, x._12); A13.pack(packer, x._13)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 13) {
          val result = zeroapply.DisjunctionApply.apply13(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker), A6.unpack(unpacker), A7.unpack(unpacker), A8.unpack(unpacker), A9.unpack(unpacker), A10.unpack(unpacker), A11.unpack(unpacker), A12.unpack(unpacker), A13.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(13, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13]): MsgpackCodec[Z] =
    codec13(construct, extract)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13)


  def codec14[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(14)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5); A6.pack(packer, x._6); A7.pack(packer, x._7); A8.pack(packer, x._8); A9.pack(packer, x._9); A10.pack(packer, x._10); A11.pack(packer, x._11); A12.pack(packer, x._12); A13.pack(packer, x._13); A14.pack(packer, x._14)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 14) {
          val result = zeroapply.DisjunctionApply.apply14(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker), A6.unpack(unpacker), A7.unpack(unpacker), A8.unpack(unpacker), A9.unpack(unpacker), A10.unpack(unpacker), A11.unpack(unpacker), A12.unpack(unpacker), A13.unpack(unpacker), A14.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(14, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14]): MsgpackCodec[Z] =
    codec14(construct, extract)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14)


  def codec15[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(15)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5); A6.pack(packer, x._6); A7.pack(packer, x._7); A8.pack(packer, x._8); A9.pack(packer, x._9); A10.pack(packer, x._10); A11.pack(packer, x._11); A12.pack(packer, x._12); A13.pack(packer, x._13); A14.pack(packer, x._14); A15.pack(packer, x._15)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 15) {
          val result = zeroapply.DisjunctionApply.apply15(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker), A6.unpack(unpacker), A7.unpack(unpacker), A8.unpack(unpacker), A9.unpack(unpacker), A10.unpack(unpacker), A11.unpack(unpacker), A12.unpack(unpacker), A13.unpack(unpacker), A14.unpack(unpacker), A15.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(15, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15]): MsgpackCodec[Z] =
    codec15(construct, extract)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15)


  def codec16[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(16)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5); A6.pack(packer, x._6); A7.pack(packer, x._7); A8.pack(packer, x._8); A9.pack(packer, x._9); A10.pack(packer, x._10); A11.pack(packer, x._11); A12.pack(packer, x._12); A13.pack(packer, x._13); A14.pack(packer, x._14); A15.pack(packer, x._15); A16.pack(packer, x._16)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 16) {
          val result = zeroapply.DisjunctionApply.apply16(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker), A6.unpack(unpacker), A7.unpack(unpacker), A8.unpack(unpacker), A9.unpack(unpacker), A10.unpack(unpacker), A11.unpack(unpacker), A12.unpack(unpacker), A13.unpack(unpacker), A14.unpack(unpacker), A15.unpack(unpacker), A16.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(16, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16]): MsgpackCodec[Z] =
    codec16(construct, extract)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16)


  def codec17[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(17)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5); A6.pack(packer, x._6); A7.pack(packer, x._7); A8.pack(packer, x._8); A9.pack(packer, x._9); A10.pack(packer, x._10); A11.pack(packer, x._11); A12.pack(packer, x._12); A13.pack(packer, x._13); A14.pack(packer, x._14); A15.pack(packer, x._15); A16.pack(packer, x._16); A17.pack(packer, x._17)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 17) {
          val result = zeroapply.DisjunctionApply.apply17(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker), A6.unpack(unpacker), A7.unpack(unpacker), A8.unpack(unpacker), A9.unpack(unpacker), A10.unpack(unpacker), A11.unpack(unpacker), A12.unpack(unpacker), A13.unpack(unpacker), A14.unpack(unpacker), A15.unpack(unpacker), A16.unpack(unpacker), A17.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(17, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17]): MsgpackCodec[Z] =
    codec17(construct, extract)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17)


  def codec18[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(18)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5); A6.pack(packer, x._6); A7.pack(packer, x._7); A8.pack(packer, x._8); A9.pack(packer, x._9); A10.pack(packer, x._10); A11.pack(packer, x._11); A12.pack(packer, x._12); A13.pack(packer, x._13); A14.pack(packer, x._14); A15.pack(packer, x._15); A16.pack(packer, x._16); A17.pack(packer, x._17); A18.pack(packer, x._18)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 18) {
          val result = zeroapply.DisjunctionApply.apply18(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker), A6.unpack(unpacker), A7.unpack(unpacker), A8.unpack(unpacker), A9.unpack(unpacker), A10.unpack(unpacker), A11.unpack(unpacker), A12.unpack(unpacker), A13.unpack(unpacker), A14.unpack(unpacker), A15.unpack(unpacker), A16.unpack(unpacker), A17.unpack(unpacker), A18.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(18, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18]): MsgpackCodec[Z] =
    codec18(construct, extract)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18)


  def codec19[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18], A19: MsgpackCodec[A19]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(19)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5); A6.pack(packer, x._6); A7.pack(packer, x._7); A8.pack(packer, x._8); A9.pack(packer, x._9); A10.pack(packer, x._10); A11.pack(packer, x._11); A12.pack(packer, x._12); A13.pack(packer, x._13); A14.pack(packer, x._14); A15.pack(packer, x._15); A16.pack(packer, x._16); A17.pack(packer, x._17); A18.pack(packer, x._18); A19.pack(packer, x._19)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 19) {
          val result = zeroapply.DisjunctionApply.apply19(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker), A6.unpack(unpacker), A7.unpack(unpacker), A8.unpack(unpacker), A9.unpack(unpacker), A10.unpack(unpacker), A11.unpack(unpacker), A12.unpack(unpacker), A13.unpack(unpacker), A14.unpack(unpacker), A15.unpack(unpacker), A16.unpack(unpacker), A17.unpack(unpacker), A18.unpack(unpacker), A19.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(19, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18], A19: MsgpackCodec[A19]): MsgpackCodec[Z] =
    codec19(construct, extract)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19)


  def codec20[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18], A19: MsgpackCodec[A19], A20: MsgpackCodec[A20]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(20)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5); A6.pack(packer, x._6); A7.pack(packer, x._7); A8.pack(packer, x._8); A9.pack(packer, x._9); A10.pack(packer, x._10); A11.pack(packer, x._11); A12.pack(packer, x._12); A13.pack(packer, x._13); A14.pack(packer, x._14); A15.pack(packer, x._15); A16.pack(packer, x._16); A17.pack(packer, x._17); A18.pack(packer, x._18); A19.pack(packer, x._19); A20.pack(packer, x._20)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 20) {
          val result = zeroapply.DisjunctionApply.apply20(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker), A6.unpack(unpacker), A7.unpack(unpacker), A8.unpack(unpacker), A9.unpack(unpacker), A10.unpack(unpacker), A11.unpack(unpacker), A12.unpack(unpacker), A13.unpack(unpacker), A14.unpack(unpacker), A15.unpack(unpacker), A16.unpack(unpacker), A17.unpack(unpacker), A18.unpack(unpacker), A19.unpack(unpacker), A20.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(20, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18], A19: MsgpackCodec[A19], A20: MsgpackCodec[A20]): MsgpackCodec[Z] =
    codec20(construct, extract)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20)


  def codec21[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18], A19: MsgpackCodec[A19], A20: MsgpackCodec[A20], A21: MsgpackCodec[A21]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(21)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5); A6.pack(packer, x._6); A7.pack(packer, x._7); A8.pack(packer, x._8); A9.pack(packer, x._9); A10.pack(packer, x._10); A11.pack(packer, x._11); A12.pack(packer, x._12); A13.pack(packer, x._13); A14.pack(packer, x._14); A15.pack(packer, x._15); A16.pack(packer, x._16); A17.pack(packer, x._17); A18.pack(packer, x._18); A19.pack(packer, x._19); A20.pack(packer, x._20); A21.pack(packer, x._21)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 21) {
          val result = zeroapply.DisjunctionApply.apply21(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker), A6.unpack(unpacker), A7.unpack(unpacker), A8.unpack(unpacker), A9.unpack(unpacker), A10.unpack(unpacker), A11.unpack(unpacker), A12.unpack(unpacker), A13.unpack(unpacker), A14.unpack(unpacker), A15.unpack(unpacker), A16.unpack(unpacker), A17.unpack(unpacker), A18.unpack(unpacker), A19.unpack(unpacker), A20.unpack(unpacker), A21.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(21, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18], A19: MsgpackCodec[A19], A20: MsgpackCodec[A20], A21: MsgpackCodec[A21]): MsgpackCodec[Z] =
    codec21(construct, extract)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21)


  def codec22[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18], A19: MsgpackCodec[A19], A20: MsgpackCodec[A20], A21: MsgpackCodec[A21], A22: MsgpackCodec[A22]): MsgpackCodec[Z] =
    MsgpackCodec.codec(
      (packer, z) => {
        packer.packArrayHeader(22)
        val x = extract(z).get
        A1.pack(packer, x._1); A2.pack(packer, x._2); A3.pack(packer, x._3); A4.pack(packer, x._4); A5.pack(packer, x._5); A6.pack(packer, x._6); A7.pack(packer, x._7); A8.pack(packer, x._8); A9.pack(packer, x._9); A10.pack(packer, x._10); A11.pack(packer, x._11); A12.pack(packer, x._12); A13.pack(packer, x._13); A14.pack(packer, x._14); A15.pack(packer, x._15); A16.pack(packer, x._16); A17.pack(packer, x._17); A18.pack(packer, x._18); A19.pack(packer, x._19); A20.pack(packer, x._20); A21.pack(packer, x._21); A22.pack(packer, x._22)
        packer.arrayEnd()
      }
    ,
      unpacker => {
        val size = unpacker.unpackArrayHeader
        if(size == 22) {
          val result = zeroapply.DisjunctionApply.apply22(
            A1.unpack(unpacker), A2.unpack(unpacker), A3.unpack(unpacker), A4.unpack(unpacker), A5.unpack(unpacker), A6.unpack(unpacker), A7.unpack(unpacker), A8.unpack(unpacker), A9.unpack(unpacker), A10.unpack(unpacker), A11.unpack(unpacker), A12.unpack(unpacker), A13.unpack(unpacker), A14.unpack(unpacker), A15.unpack(unpacker), A16.unpack(unpacker), A17.unpack(unpacker), A18.unpack(unpacker), A19.unpack(unpacker), A20.unpack(unpacker), A21.unpack(unpacker), A22.unpack(unpacker)
          )(construct)
          unpacker.arrayEnd()
          result
        }else{
          -\/(new UnexpectedArraySize(22, size))
        }
      }
    )

  def codec[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, Z](construct: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22) => Z, extract: Z => Option[(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22)])(implicit A1: MsgpackCodec[A1], A2: MsgpackCodec[A2], A3: MsgpackCodec[A3], A4: MsgpackCodec[A4], A5: MsgpackCodec[A5], A6: MsgpackCodec[A6], A7: MsgpackCodec[A7], A8: MsgpackCodec[A8], A9: MsgpackCodec[A9], A10: MsgpackCodec[A10], A11: MsgpackCodec[A11], A12: MsgpackCodec[A12], A13: MsgpackCodec[A13], A14: MsgpackCodec[A14], A15: MsgpackCodec[A15], A16: MsgpackCodec[A16], A17: MsgpackCodec[A17], A18: MsgpackCodec[A18], A19: MsgpackCodec[A19], A20: MsgpackCodec[A20], A21: MsgpackCodec[A21], A22: MsgpackCodec[A22]): MsgpackCodec[Z] =
    codec22(construct, extract)(A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22)

}
