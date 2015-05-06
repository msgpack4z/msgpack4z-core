import sbt._, Keys._

object build extends Build {

  private val msgpack4zCoreName = "msgpack4z-core"
  val modules = msgpack4zCoreName :: Nil

  private val shapelessContrib = "0.3"

  lazy val msgpack4z = Project("msgpack4z-core", file(".")).settings(
    Common.settings ++ Generator.settings: _*
  ).settings(
    name := msgpack4zCoreName,
    libraryDependencies ++= (
      ("com.github.xuwei-k" % "msgpack4z-api" % "0.1.0") ::
      ("org.scalaz" %% "scalaz-core" % Common.ScalazVersion) ::
      ("org.scalaz" %% "scalaz-scalacheck-binding" % Common.ScalazVersion % "test") ::
      ("com.github.xuwei-k" %% "zeroapply-scalaz" % "0.1.2" % "provided") ::
      ("org.typelevel" %% "shapeless-scalaz" % shapelessContrib % "test") ::
      ("org.typelevel" %% "shapeless-scalacheck" % shapelessContrib % "test") ::
      ("com.github.xuwei-k" % "msgpack4z-java07" % "0.1.3" % "test").exclude("org.msgpack", "msgpack-core") ::
      ("org.msgpack" % "msgpack-core" % "0.7.0-p9" % "test") ::
      ("com.github.xuwei-k" % "msgpack4z-java06" % "0.1.0" % "test") ::
      ("com.github.xuwei-k" %% "msgpack4z-native" % "0.1.0" % "test") ::
      Nil
    )
  ).settings(
    Sxr.subProjectSxr(Compile, "classes.sxr"): _*
  )

}
