package msgpack4z

import org.openjdk.jmh.annotations.Benchmark

abstract class UnpackBenchmarkBase {

  protected[this] def bench(createUnpacker: Array[Byte] => MsgUnpacker): Unit

  @Benchmark
  def java06(): Unit =
    bench(Msgpack06.defaultUnpacker)

  @Benchmark
  def java07(): Unit =
    bench(Msgpack07Unpacker.defaultUnpacker)

  @Benchmark
  def native_(): Unit =
    bench(MsgInBuffer(_))
}

abstract class PackBenchmarkBase {

  protected[this] def bench(createPacker: () => MsgPacker): Unit

  @Benchmark
  def java06(): Unit =
    bench(() => Msgpack06.defaultPacker())

  @Benchmark
  def java07(): Unit =
    bench(() => new Msgpack07Packer())

  @Benchmark
  def native_(): Unit =
    bench(() => MsgOutBuffer.create())
}

class PackBenchmark extends PackBenchmarkBase {
  override def bench(createPacker: () => MsgPacker) =
    Msgpack4zBenchmark.packValues(createPacker)
}

class UnpackBenchmark extends UnpackBenchmarkBase {
  override def bench (createUnpacker: Array[Byte] => MsgUnpacker): Unit =
    Msgpack4zBenchmark.unpackValues(createUnpacker)
}

class PackLargeArrayBenchmark extends PackBenchmarkBase {
  override def bench(createPacker: () => MsgPacker) = {
    val packer = createPacker()
    MsgpackCodec[MsgpackUnion].pack(packer, Msgpack4zBenchmark.unionLargeArray)
    packer.result()
  }
}

class PackLargeMapBenchmark extends PackBenchmarkBase {
  override def bench(createPacker: () => MsgPacker) = {
    val packer = createPacker()
    MsgpackCodec[MsgpackUnion].pack(packer, Msgpack4zBenchmark.unionLargeMap)
    packer.result()
  }
}
