import sbt._

object build {

  val msgpack4zCoreName = "msgpack4z-core"
  val modules = msgpack4zCoreName :: Nil

  val mimaBasis = SettingKey[String]("mimaBasis")

}
