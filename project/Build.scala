import sbt._, Keys._

import com.typesafe.sbt.pgp.PgpKeys
import com.typesafe.tools.mima.plugin.MimaPlugin
import org.scalajs.sbtplugin.cross.CrossProject
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object build {

  val msgpack4zCoreName = "msgpack4z-core"
  val modules = msgpack4zCoreName :: Nil

  val mimaBasis = SettingKey[String]("mimaBasis")

  val msgpack4zNativeVersion = "0.3.0"
  val scalapropsVersion = "0.3.3"

  lazy val msgpack4z = CrossProject("msgpack4z-core", file("."), CustomCrossType).settings(
    Seq(
      MimaPlugin.mimaDefaultSettings,
      Common.settings,
      Generator.settings
    ).flatten : _*
  ).settings(
    name := msgpack4zCoreName,
    libraryDependencies ++= (
      ("org.scalaz" %%% "scalaz-core" % Common.ScalazVersion) ::
      ("com.github.xuwei-k" %% "zeroapply-scalaz" % "0.2.1" % "provided") ::
      ("com.github.scalaprops" %%% "scalaprops" % scalapropsVersion % "test") ::
      ("com.github.scalaprops" %%% "scalaprops-scalazlaws" % scalapropsVersion % "test") ::
      Nil
    )
  ).enablePlugins(
    sbtbuildinfo.BuildInfoPlugin
  ).jvmSettings(
    Sxr.settings : _*
  ).jvmSettings(
    libraryDependencies ++= (
      ("com.github.xuwei-k" % "msgpack4z-api" % "0.2.0") ::
      ("com.github.xuwei-k" % "msgpack4z-java06" % "0.2.0" % "test") ::
      ("com.github.xuwei-k" %% "msgpack4z-native" % msgpack4zNativeVersion % "test") ::
      Nil
    )
  ).jsSettings(
    scalacOptions += {
      val a = (baseDirectory in LocalRootProject).value.toURI.toString
      val g = "https://raw.githubusercontent.com/msgpack4z/msgpack4z-core/" + Common.tagOrHash.value
      s"-P:scalajs:mapSourceURI:$a->$g/"
    },
    scalaJSSemantics ~= { _.withStrictFloats(true) },
    scalaJSStage in Test := FastOptStage,
    scalaJSUseRhino in Global := false,
    jsEnv := NodeJSEnv().value,
    libraryDependencies ++= (
      ("com.github.xuwei-k" %%% "msgpack4z-native" % msgpack4zNativeVersion) ::
      Nil
    )
  )

  lazy val noPublish = Seq(
    PgpKeys.publishSigned := {},
    PgpKeys.publishLocalSigned := {},
    publishLocal := {},
    publish := {},
    publishArtifact in Compile := false,
    publishArtifact in Test := false
  )
}
