package msgpack4z

import org.openjdk.jmh.annotations.Benchmark

class UnpackPackBenchmark{

  @Benchmark
  def java06(): Unit =
    Msgpack4zBenchmark.unpackPack(() => Msgpack06.defaultPacker(), Msgpack06.defaultUnpacker(_))

  @Benchmark
  def java08(): Unit =
    Msgpack4zBenchmark.unpackPack(() => new MsgpackJavaPacker(), MsgpackJavaUnpacker.defaultUnpacker(_))

  @Benchmark
  def native_(): Unit =
    Msgpack4zBenchmark.unpackPack(() => MsgOutBuffer.create(), MsgInBuffer(_))

  @Benchmark
  def scodecMsgpack(): Unit = {
    val a = scodec.msgpack.codecs.MessagePackCodec.decode(Msgpack4zBenchmark.unionLargeArrayBitVector).require.value
    scodec.msgpack.codecs.MessagePackCodec.encode(a).require
  }
}

