import sbt._, Keys._

import com.typesafe.tools.mima.plugin.MimaPlugin

object build extends Build {

  private val msgpack4zCoreName = "msgpack4z-core"
  val modules = msgpack4zCoreName :: Nil

  val mimaBasis = SettingKey[String]("mimaBasis")

  lazy val msgpack4z = Project("msgpack4z-core", file(".")).settings(
    MimaPlugin.mimaDefaultSettings
  ).settings(
    Common.settings
  ).settings(
    Generator.settings
  ).settings(
    name := msgpack4zCoreName,
    libraryDependencies ++= (
      ("com.github.xuwei-k" % "msgpack4z-api" % "0.2.0") ::
      ("org.scalaz" %% "scalaz-core" % Common.ScalazVersion) ::
      ("com.github.xuwei-k" %% "zeroapply-scalaz" % "0.1.4" % "provided") ::
      ("com.github.xuwei-k" % "msgpack4z-java07" % "0.2.0" % "test") ::
      ("com.github.xuwei-k" % "msgpack4z-java06" % "0.2.0" % "test") ::
      ("com.github.xuwei-k" %% "msgpack4z-native" % "0.2.1" % "test") ::
      Nil
    )
  ).settings(
    Sxr.settings
  )

}
