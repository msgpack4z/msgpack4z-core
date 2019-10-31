package msgpack4z

import java.util.Arrays
import scalaz.\/-

final class Binary(val value: Array[Byte]) {
  override def toString: String = hexString("Binary(size = " + value.length + " value = ", " ", ")", 4)

  def hexString(start: String, sep: String, end: String, n: Int): String = {
    value.sliding(n, n).map(_.map(x => "%02x".format(x & 0xff)).mkString).mkString(start, sep, end)
  }

  def ===(that: Binary): Boolean = {
    if (this eq that)
      true
    else
      Arrays.equals(this.value, that.value)
  }

  override def equals(other: Any): Boolean = other match {
    case that: Binary =>
      this.===(that)
    case _ =>
      false
  }

  override def hashCode: Int = Arrays.hashCode(value)
}

trait BinaryCodec {
  /**
   * @see [[https://github.com/msgpack/msgpack/blob/master/spec.md#formats-bin]]
   */
  implicit def binaryCodec: MsgpackCodec[Binary]
}

private[msgpack4z] trait BinaryCodecImpl extends BinaryCodec {
  override final val binaryCodec: MsgpackCodec[Binary] = MsgpackCodec.tryConstE(
    (packer, binary) => {
      packer.packBinary(binary.value)
    },
    unpacker => \/-(new Binary(unpacker.unpackBinary()))
  )
}
