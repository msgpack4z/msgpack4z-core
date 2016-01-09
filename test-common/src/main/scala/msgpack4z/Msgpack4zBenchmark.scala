package msgpack4z

import scodec.bits.BitVector

import scalaz.{-\/, \/-}

object Msgpack4zBenchmark {

  private[this] def gen(size: Int) =
    UnionGen.unionWithoutExtGen.samples(listSize = size)

  val unionValues: List[MsgpackUnion] =
    gen(1000)

  val unionLargeArray: MsgpackUnion =
    MsgpackArray(unionValues)

  val unionLargeArrayBinary: Array[Byte] = {
    val packer = MsgOutBuffer.create()
    MsgpackCodec[MsgpackUnion].pack(packer, unionLargeArray)
    packer.result()
  }

  val unionLargeArrayBitVector = BitVector(unionLargeArrayBinary)

  def unpackPack(createPacker: () => MsgPacker, createUnpacker: Array[Byte] => MsgUnpacker): Unit = {
    val unpacker = createUnpacker(unionLargeArrayBinary)
    val packer = createPacker()
    val F = MsgpackCodec[MsgpackUnion]
    F.unpackAndClose(unpacker) match {
      case \/-(value) =>
        F.pack(packer, value)
        packer.result()
      case -\/(e) =>
        throw e
    }
  }
}
