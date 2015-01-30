package msgpack4z

abstract class PackerUnpackerFactory{
  def packer: MsgPacker
  def unpacker(bytes: Array[Byte]): MsgUnpacker
}
