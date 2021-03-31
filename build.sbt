import build._
import sbtrelease.ReleaseStateTransformations._
import sbtcrossproject.CrossProject

val msgpack4zNativeVersion = "0.3.7"
val scalapropsVersion = "0.8.2"
def ScalazVersion = "7.3.3"
def Scala211 = "2.11.12"

val tagName = Def.setting {
  s"v${if (releaseUseGlobalVersion.value) (ThisBuild / version).value else version.value}"
}
val tagOrHash = Def.setting {
  if (isSnapshot.value) gitHash() else tagName.value
}

def gitHash(): String =
  sys.process.Process("git rev-parse HEAD").lineStream_!.head

val unusedWarnings = Seq(
  "-Ywarn-unused",
)

val setMimaVersion: State => State = { st =>
  val extracted = Project.extract(st)
  val (releaseV, _) = st.get(ReleaseKeys.versions).getOrElse(sys.error("impossible"))
  IO.write(
    extracted get releaseVersionFile,
    s"""\nThisBuild / build.${build.mimaBasis.key.label} := \"${releaseV}\"\n""",
    append = true
  )
  reapply(Seq(ThisBuild / build.mimaBasis := releaseV), st)
}

val commonSettings = Def.settings(
  Test / sources := {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, _)) =>
        (Test / sources).value
      case _ =>
        Nil // TODO https://github.com/msgpack4z/msgpack4z-core/issues/134
    }
  },
  ReleasePlugin.extraReleaseCommands,
  scalapropsCoreSettings,
  publishTo := sonatypePublishToBundle.value,
  fullResolvers ~= { _.filterNot(_.name == "jcenter") },
  buildInfoKeys := Seq[BuildInfoKey](
    organization,
    name,
    version,
    scalaVersion,
    sbtVersion,
    licenses,
    "scalazVersion" -> ScalazVersion
  ),
  buildInfoPackage := "msgpack4z",
  buildInfoObject := "BuildInfoMsgpack4zCore",
  commands += Command.command("updateReadme")(UpdateReadme.updateReadmeTask),
  commands += Command.command("setMimaVersion")(setMimaVersion),
  mimaPreviousArtifacts := {
    build.mimaBasis.?.value match {
      case Some(v) =>
        Set(organization.value % (name.value + "_" + scalaBinaryVersion.value) % v)
      case None =>
        Set.empty
    }
  },
  releaseTagName := tagName.value,
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    UpdateReadme.updateReadmeProcess,
    tagRelease,
    ReleaseStep(
      action = { state =>
        val extracted = Project extract state
        extracted.runAggregated(extracted.get(thisProjectRef) / (Global / PgpKeys.publishSigned), state)
      },
      enableCrossBuild = true
    ),
    releaseStepCommandAndRemaining("+ " + build.msgpack4zCoreName + "Native/publishSigned"),
    releaseStepCommandAndRemaining("sonatypeBundleRelease"),
    setNextVersion,
    setMimaVersion,
    commitNextVersion,
    UpdateReadme.updateReadmeProcess,
    pushChanges
  ),
  credentials ++= PartialFunction
    .condOpt(sys.env.get("SONATYPE_USER") -> sys.env.get("SONATYPE_PASS")) { case (Some(user), Some(pass)) =>
      Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", user, pass)
    }
    .toList,
  organization := "com.github.xuwei-k",
  homepage := Some(url("https://github.com/msgpack4z")),
  licenses := Seq("MIT License" -> url("http://www.opensource.org/licenses/mit-license.php")),
  scalacOptions ++= Seq(
    "-target:jvm-1.8",
    "-deprecation",
    "-unchecked",
    "-Xlint",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
  ),
  scalacOptions ++= unusedWarnings,
  scalacOptions ++= PartialFunction
    .condOpt(CrossVersion.partialVersion(scalaVersion.value)) {
      case Some((2, v)) if v <= 12 =>
        Seq(
          "-Xfuture",
          "-Yno-adapted-args"
        )
    }
    .toList
    .flatten,
  scalaVersion := Scala211,
  crossScalaVersions := Scala211 :: "2.12.13" :: "2.13.5" :: "3.0.0-RC2" :: Nil,
  (Compile / doc / scalacOptions) ++= {
    val tag = tagOrHash.value
    Seq(
      "-sourcepath",
      (LocalRootProject / baseDirectory).value.getAbsolutePath,
      "-doc-source-url",
      s"https://github.com/msgpack4z/msgpack4z-core/tree/${tag}€{FILE_PATH}.scala"
    )
  },
  pomExtra :=
    <developers>
      <developer>
        <id>xuwei-k</id>
        <name>Kenji Yoshida</name>
        <url>https://github.com/xuwei-k</url>
      </developer>
    </developers>
    <scm>
      <url>git@github.com:msgpack4z/msgpack4z-core.git</url>
      <connection>scm:git:git@github.com:msgpack4z/msgpack4z-core.git</connection>
      <tag>{tagOrHash.value}</tag>
    </scm>,
  description := "typeclass based msgpack serializer/deserializer",
  pomPostProcess := { node =>
    import scala.xml._
    import scala.xml.transform._
    def stripIf(f: Node => Boolean) =
      new RewriteRule {
        override def transform(n: Node) =
          if (f(n)) NodeSeq.Empty else n
      }
    val stripTestScope = stripIf { n => n.label == "dependency" && (n \ "scope").text == "test" }
    new RuleTransformer(stripTestScope).transform(node)(0)
  }
) ++ Seq(Compile, Test).flatMap(c => c / console / scalacOptions --= unusedWarnings)

lazy val msgpack4zCore = CrossProject(
  id = msgpack4zCoreName,
  base = file(".")
)(
  JSPlatform,
  JVMPlatform,
  NativePlatform
).crossType(
  CustomCrossType
).settings(
  commonSettings,
  Generator.settings,
  name := msgpack4zCoreName,
  libraryDependencies ++= Seq(
    "org.scalaz" %%% "scalaz-core" % ScalazVersion withDottyCompat scalaVersion.value,
    "com.github.scalaprops" %%% "scalaprops" % scalapropsVersion % "test" withDottyCompat scalaVersion.value,
    "com.github.scalaprops" %%% "scalaprops-scalaz" % scalapropsVersion % "test" withDottyCompat scalaVersion.value,
  ),
  libraryDependencies += {
    val x = "com.github.xuwei-k" %% "zeroapply-scalaz" % "0.4.1"
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, _)) =>
        x % "provided"
      case _ =>
        x // https://github.com/xuwei-k/zeroapply/issues/104
    }
  }
).enablePlugins(
  MimaPlugin,
  sbtbuildinfo.BuildInfoPlugin
).jvmSettings(
  Sxr.settings,
  libraryDependencies ++= Seq(
    "com.github.xuwei-k" % "msgpack4z-api" % "0.2.0",
    "com.github.xuwei-k" % "msgpack4z-java06" % "0.2.0" % "test",
    "com.github.xuwei-k" %% "msgpack4z-native" % msgpack4zNativeVersion % "test" withDottyCompat scalaVersion.value,
  )
).jsSettings(
  scalacOptions += {
    val a = (LocalRootProject / baseDirectory).value.toURI.toString
    val g = "https://raw.githubusercontent.com/msgpack4z/msgpack4z-core/" + tagOrHash.value
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, _)) =>
        s"-P:scalajs:mapSourceURI:$a->$g/"
      case _ =>
        s"-scalajs-mapSourceURI:$a->$g/"
    }
  },
  scalaJSLinkerConfig ~= { _.withSemantics(_.withStrictFloats(true)) },
).platformsSettings(NativePlatform, JSPlatform)(
  libraryDependencies ++= Seq(
    "com.github.xuwei-k" %%% "msgpack4z-native" % msgpack4zNativeVersion withDottyCompat scalaVersion.value,
  )
)

lazy val noPublish = Seq(
  mimaPreviousArtifacts := Set.empty,
  PgpKeys.publishSigned := {},
  PgpKeys.publishLocalSigned := {},
  publishLocal := {},
  publish := {},
  Compile / publishArtifact := false,
  Test / publishArtifact := false
)

lazy val msgpack4zCoreJVM = msgpack4zCore.jvm
lazy val msgpack4zCoreJS = msgpack4zCore.js
lazy val msgpack4zCoreNative = msgpack4zCore.native.settings(
  scalapropsNativeSettings,
  crossScalaVersions ~= (_.filter(_ startsWith "2.1"))
)

val subProjects = List(
  msgpack4zCoreJVM,
  msgpack4zCoreJS,
  testJava07,
  testJavaLatest
)

lazy val root = Project("root", file("."))
  .settings(
    commonSettings,
    noPublish,
    commands += Command.command("testSequential") {
      subProjects.map(_.id).map(_ + "/test").sorted ::: _
    },
    commands += Command.command("testSequentialCross") {
      subProjects.map(_.id).map("+ " + _ + "/test").sorted ::: _
    },
    Compile / scalaSource := (ThisBuild / baseDirectory).value / "dummy",
    Test / scalaSource := (ThisBuild / baseDirectory).value / "dummy"
  )
  .aggregate(
    msgpack4zCoreJVM,
    msgpack4zCoreJS,
    testJava07,
    testJavaLatest,
  )

lazy val testJava07 = Project("testJava07", file("test-java07"))
  .settings(
    commonSettings,
    noPublish,
    libraryDependencies ++= Seq(
      "com.github.xuwei-k" % "msgpack4z-java07" % "0.2.0" % "test",
    )
  )
  .dependsOn(msgpack4zCoreJVM % "test->test")

lazy val testJavaLatest = Project("testJavaLatest", file("test-java-latest"))
  .settings(
    commonSettings,
    noPublish,
    libraryDependencies ++= Seq(
      ("com.github.xuwei-k" % "msgpack4z-java" % "0.3.6" % "test"),
    )
  )
  .dependsOn(msgpack4zCoreJVM % "test->test")
