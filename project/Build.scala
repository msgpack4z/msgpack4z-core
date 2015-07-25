import sbt._, Keys._

object build extends Build {

  private val msgpack4zCoreName = "msgpack4z-core"
  val modules = msgpack4zCoreName :: Nil

  lazy val msgpack4z = Project("msgpack4z-core", file(".")).settings(
    Common.settings ++ Generator.settings: _*
  ).settings(
    name := msgpack4zCoreName,
    libraryDependencies ++= (
      ("com.github.xuwei-k" % "msgpack4z-api" % "0.1.0") ::
      ("org.scalaz" %% "scalaz-core" % Common.ScalazVersion) ::
      ("com.github.xuwei-k" %% "zeroapply-scalaz" % "0.1.3" % "provided") ::
      ("com.github.xuwei-k" % "msgpack4z-java07" % "0.1.4" % "test") ::
      ("com.github.xuwei-k" % "msgpack4z-java06" % "0.1.1" % "test") ::
      ("com.github.xuwei-k" %% "msgpack4z-native" % "0.1.1" % "test") ::
      Nil
    )
  ).settings(
    Sxr.settings
  )

}
