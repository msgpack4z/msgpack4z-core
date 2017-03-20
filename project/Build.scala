import sbt._, Keys._

import com.typesafe.sbt.pgp.PgpKeys
import com.typesafe.tools.mima.plugin.MimaPlugin
import com.typesafe.tools.mima.plugin.MimaKeys.mimaPreviousArtifacts
import scalanative.sbtplugin.ScalaNativePlugin.autoImport._
import scalajscrossproject.ScalaJSCrossPlugin.autoImport.{toScalaJSGroupID => _, _}
import sbtcrossproject.CrossProject
import sbtcrossproject.CrossPlugin.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport.{scalaJSOptimizerOptions, scalaJSSemantics}

object build {

  val msgpack4zCoreName = "msgpack4z-core"
  val modules = msgpack4zCoreName :: Nil

  val mimaBasis = SettingKey[String]("mimaBasis")

  val msgpack4zNativeVersion = "0.3.3"
  val scalapropsVersion = "0.4.2"

  val nativeTestId = "nativeTest"

  lazy val msgpack4zCore = CrossProject(
    id = msgpack4zCoreName,
    base = file("."),
    crossType = CustomCrossType,
    platforms = JSPlatform, JVMPlatform, NativePlatform
  ).settings(
    MimaPlugin.mimaDefaultSettings,
    Common.settings,
    Generator.settings,
    name := msgpack4zCoreName,
    libraryDependencies ++= (
      ("org.scalaz" %%% "scalaz-core" % Common.ScalazVersion) ::
      ("com.github.xuwei-k" %% "zeroapply-scalaz" % "0.2.2" % "provided") ::
      Nil
    )
  ).enablePlugins(
    sbtbuildinfo.BuildInfoPlugin
  ).jvmSettings(
    Sxr.settings,
    libraryDependencies ++= (
      ("com.github.xuwei-k" % "msgpack4z-api" % "0.2.0") ::
      ("com.github.xuwei-k" % "msgpack4z-java06" % "0.2.0" % "test") ::
      ("com.github.xuwei-k" %% "msgpack4z-native" % msgpack4zNativeVersion % "test") ::
      Nil
    )
  ).jsSettings(
    scalaJSOptimizerOptions ~= { options =>
      // https://github.com/scala-js/scala-js/issues/2798
      try {
        scala.util.Properties.isJavaAtLeast("1.8")
        options
      } catch {
        case _: NumberFormatException =>
          options.withParallel(false)
      }
    },
    scalacOptions += {
      val a = (baseDirectory in LocalRootProject).value.toURI.toString
      val g = "https://raw.githubusercontent.com/msgpack4z/msgpack4z-core/" + Common.tagOrHash.value
      s"-P:scalajs:mapSourceURI:$a->$g/"
    },
    scalaJSSemantics ~= { _.withStrictFloats(true) }
  ).platformsSettings(JVMPlatform, JSPlatform)(
    libraryDependencies ++= (
      ("com.github.scalaprops" %%% "scalaprops" % scalapropsVersion % "test") ::
      ("com.github.scalaprops" %%% "scalaprops-scalazlaws" % scalapropsVersion % "test") ::
      Nil
    )
  ).platformsSettings(NativePlatform, JSPlatform)(
    libraryDependencies ++= (
      ("com.github.xuwei-k" %%% "msgpack4z-native" % msgpack4zNativeVersion) ::
      Nil
    )
  ).nativeSettings(
    sources in Test := Nil
  )

  lazy val noPublish = Seq(
    mimaPreviousArtifacts := Set.empty,
    PgpKeys.publishSigned := {},
    PgpKeys.publishLocalSigned := {},
    publishLocal := {},
    publish := {},
    publishArtifact in Compile := false,
    publishArtifact in Test := false
  )
}
