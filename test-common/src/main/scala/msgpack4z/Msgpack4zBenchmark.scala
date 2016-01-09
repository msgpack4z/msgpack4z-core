package msgpack4z

import scalaz.{-\/, \/-}

object Msgpack4zBenchmark {

  private[this] def gen(size: Int) =
    UnionGen.unionWithoutExtGen.samples(listSize = size)

  val unionValues: List[MsgpackUnion] =
    gen(10000)

  val unionLargeArray: MsgpackUnion =
    MsgpackArray(unionValues)

  val unionLargeMap: MsgpackUnion = {
    val size = 10000
    MsgpackMap(
      (gen(size), gen(size)).zipped.toMap
    )
  }

  val binaryValues: List[Array[Byte]] =
    unionValues.map{ value =>
      val packer = MsgOutBuffer.create()
      MsgpackCodec[MsgpackUnion].pack(packer, value)
      packer.result()
    }

  def unpackValues(createUnpacker: Array[Byte] => MsgUnpacker): List[MsgpackUnion] = {
    val F = MsgpackCodec[MsgpackUnion]
    def loop(xs: List[Array[Byte]], acc: List[MsgpackUnion]): List[MsgpackUnion] =
      xs match {
        case h :: t =>
          val unpacker = createUnpacker(h)
          F.unpackAndClose(unpacker) match {
            case \/-(v) =>
              loop(t, v :: acc)
            case -\/(e) =>
              throw e
          }
        case _ =>
          acc
      }
    loop(Msgpack4zBenchmark.binaryValues, Nil)
  }

  def packValues(createPacker: () => MsgPacker): List[Array[Byte]] = {
    val F = MsgpackCodec[MsgpackUnion]
    def loop(xs: List[MsgpackUnion], acc: List[Array[Byte]]): List[Array[Byte]] =
      xs match {
        case h :: t =>
          val packer = createPacker()
          F.pack(packer, h)
          loop(t, packer.result() :: acc)
        case _ =>
          acc
      }
    loop(Msgpack4zBenchmark.unionValues, Nil)
  }

}
