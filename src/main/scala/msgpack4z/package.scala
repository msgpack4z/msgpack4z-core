import scalaz.\/

package object msgpack4z {
  type UnpackResult[A] = UnpackError \/ A
  type Packer[A] = (MsgPacker, A) => Unit
  type Unpacker[A] = MsgUnpacker => UnpackResult[A]
}
