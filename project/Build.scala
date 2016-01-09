import sbt._, Keys._

import com.typesafe.tools.mima.plugin.MimaPlugin
import pl.project13.scala.sbt.JmhPlugin
import scalaprops.ScalapropsPlugin.autoImport.scalapropsVersion

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
      ("com.github.xuwei-k" %% "zeroapply-scalaz" % "0.2.0" % "provided") ::
      Nil
    )
  ).settings(
    Sxr.settings
  )

  private[this] lazy val noPublish = Seq(
    publishLocal := {},
    publish := {},
    publishArtifact := false
  )

  lazy val root = Project("root", file("root")).settings(
    Common.settings,
    noPublish
  ).aggregate(
    msgpack4z, testJava07, testJavaLatest, testCommon
  )

  lazy val testCommon = Project("testCommon", file("test-common")).settings(
    Common.settings,
    noPublish,
    libraryDependencies ++= (
      ("com.github.scalaprops" %% "scalaprops" % scalapropsVersion.value) ::
      ("com.github.xuwei-k" % "msgpack4z-java06" % "0.2.0") ::
      ("com.github.xuwei-k" %% "msgpack4z-native" % "0.2.1") ::
      ("com.github.pocketberserker" %% "scodec-msgpack" % "0.4.3") ::
      Nil
    )
  ).dependsOn(msgpack4z)

  lazy val testJava07 = Project("testJava07", file("test-java07")).settings(
    Common.settings,
    noPublish,
    libraryDependencies ++= (
      ("com.github.xuwei-k" % "msgpack4z-java07" % "0.2.0") ::
      Nil
    )
  ).dependsOn(
    testCommon % "compile->compile;test->test"
  ).enablePlugins(JmhPlugin)

  lazy val testJavaLatest = Project("testJavaLatest", file("test-java-latest")).settings(
    Common.settings,
    noPublish,
    libraryDependencies ++= (
      ("com.github.xuwei-k" % "msgpack4z-java" % "0.3.0") ::
      Nil
    )
  ).dependsOn(
    testCommon % "compile->compile;test->test"
  ).enablePlugins(JmhPlugin)

}
