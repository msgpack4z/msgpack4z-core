package msgpack4z

import scalaprops._
import scalaz.\/-

object PackerUnpackerTest extends Scalaprops {
  val packerGen1: Gen[() => msgpack4z.MsgPacker] =
    Gen.elements(
      () => new Msgpack07Packer,
      () => Msgpack06.defaultPacker(),
      () => MsgOutBuffer.create()
    )

  val unpackerGen1: Gen[Array[Byte] => msgpack4z.MsgUnpacker] =
    Gen.elements(
      b => Msgpack07Unpacker.defaultUnpacker(b),
      b => Msgpack06.defaultUnpacker(b),
      b => MsgInBuffer(b)
    )

  val union1 = Property.forAllG(UnionGen.unionWithoutExtGen, packerGen1, unpackerGen1) { (data, packer, unpacker) =>
    val bytes = MsgpackCodec[MsgpackUnion].toBytes(data, packer())
    val result = MsgpackCodec[MsgpackUnion].unpackAndClose(unpacker(bytes))
    result == \/-(data)
  }

  val packerGen2: Gen[() => msgpack4z.MsgPacker] =
    Gen.elements(
      () => new Msgpack07Packer,
      () => MsgOutBuffer.create()
    )

  val unpackerGen2: Gen[Array[Byte] => msgpack4z.MsgUnpacker] =
    Gen.elements(
      b => Msgpack07Unpacker.defaultUnpacker(b),
      b => MsgInBuffer(b)
    )

  val union2 = Property.forAllG(UnionGen.unionGen, packerGen2, unpackerGen2) { (data, packer, unpacker) =>
    val bytes = MsgpackCodec[MsgpackUnion].toBytes(data, packer())
    val result = MsgpackCodec[MsgpackUnion].unpackAndClose(unpacker(bytes))
    result == \/-(data)
  }

  override def param = super.param.copy(minSuccessful = 2000, maxSize = 1000)
}
