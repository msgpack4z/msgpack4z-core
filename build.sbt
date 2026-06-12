import build._
import sbtrelease.ReleaseStateTransformations._

val msgpack4zNativeVersion = "0.4.0"
val scalapropsVersion = "0.11.0"
def ScalazVersion = "7.3.8"
def Scala213 = "2.13.18"
val scalaVersions = "2.12.21" :: Scala213 :: "3.3.8" :: Nil

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

val jsNativeCommon = Def.settings(
  libraryDependencies ++= Seq(
    "com.github.xuwei-k" %%% "msgpack4z-native" % msgpack4zNativeVersion,
  )
)

val setMimaVersion: State => State = { st =>
  val extracted = Project.extract(st)
  val (releaseV, _) = st.get(ReleaseKeys.versions).getOrElse(sys.error("impossible"))
  IO.write(
    extracted.get(releaseVersionFile),
    s"""\nThisBuild / build.${build.mimaBasis.key.label} := \"${releaseV}\"\n""",
    append = true
  )
  reapply(Seq(ThisBuild / build.mimaBasis := releaseV), st)
}

val commonSettings = Def.settings(
  ReleasePlugin.extraReleaseCommands,
  scalapropsCoreSettings,
  publishTo := (if (isSnapshot.value) None else localStaging.value),
  fullResolvers ~= { _.filterNot(_.name == "jcenter") },
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
        val extracted = Project.extract(state)
        extracted.runAggregated(extracted.get(thisProjectRef) / (Global / PgpKeys.publishSigned), state)
      },
      enableCrossBuild = false
    ),
    releaseStepCommandAndRemaining("sonaRelease"),
    setNextVersion,
    setMimaVersion,
    commitNextVersion,
    UpdateReadme.updateReadmeProcess,
    pushChanges
  ),
  organization := "com.github.xuwei-k",
  homepage := Some(url("https://github.com/msgpack4z")),
  licenses := Seq(License.MIT),
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-Wconf:msg=Implicit parameters should be provided with:error",
    "-Wconf:msg=with as a type operator has been deprecated:error",
  ),
  scalacOptions ++= PartialFunction
    .condOpt(CrossVersion.partialVersion(scalaVersion.value)) { case Some((2, _)) =>
      unusedWarnings ++ Seq(
        "-Xlint",
      )
    }
    .toList
    .flatten,
  scalacOptions ++= PartialFunction
    .condOpt(CrossVersion.partialVersion(scalaVersion.value)) {
      case Some((2, 13)) =>
        Seq(
          "-Xsource:3-cross",
        )
      case Some((2, 12)) =>
        Seq(
          "-Xsource:3",
        )
    }
    .toList
    .flatten,
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

lazy val msgpack4zCore = projectMatrix
  .defaultAxes()
  .withId(msgpack4zCoreName)
  .in(file("."))
  .settings(
    commonSettings,
    Test / parallelExecution := false,
    Generator.settings,
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
    name := msgpack4zCoreName,
    Test / sourceDirectories ~= (_.distinct),
    libraryDependencies ++= Seq(
      "org.scalaz" %%% "scalaz-core" % ScalazVersion,
      "com.github.scalaprops" %%% "scalaprops" % scalapropsVersion % "test",
      "com.github.scalaprops" %%% "scalaprops-scalaz" % scalapropsVersion % "test",
      "com.github.xuwei-k" %% "zeroapply-scalaz" % "0.5.1" % "provided",
    ),
    libraryDependencies ++= {
      if (CrossVersion.partialVersion(scalaVersion.value).exists(_._1 == 2)) {
        Seq("com.chuusai" %%% "shapeless" % "2.3.13" % "test")
      } else {
        Nil
      }
    },
  )
  .enablePlugins(
    MimaPlugin,
    sbtbuildinfo.BuildInfoPlugin
  )
  .jvmPlatform(
    scalaVersions = scalaVersions,
    settings = Def.settings(
      libraryDependencies ++= Seq(
        "com.github.xuwei-k" % "msgpack4z-api" % "0.2.0",
        "com.github.xuwei-k" % "msgpack4z-java06" % "0.2.0" % "test",
        "com.github.xuwei-k" %% "msgpack4z-native" % msgpack4zNativeVersion % "test",
      ),

    )
  )
  .jsPlatform(
    scalaVersions,
    Def.settings(
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
      jsNativeCommon
    )
  )
  .nativePlatform(
    scalaVersions,
    Def.settings(
      scalapropsNativeSettings,
      jsNativeCommon
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

TaskKey[Unit]("testSequential") := Def
  .sequential(
    List(
      msgpack4zCore.allProjects(),
      testJavaLatest.allProjects(),
    ).flatten.map(_._1).sortBy(_.id).map(_ / Test / test)
  )
  .value
commonSettings
noPublish
Compile / scalaSource := (ThisBuild / baseDirectory).value / "dummy"
Test / scalaSource := (ThisBuild / baseDirectory).value / "dummy"
autoScalaLibrary := false

lazy val testJavaLatest = projectMatrix
  .in(file("test-java-latest"))
  .settings(
    commonSettings,
    noPublish,
    libraryDependencies ++= Seq(
      ("com.github.xuwei-k" % "msgpack4z-java" % "0.4.0" % "test"),
    )
  )
  .defaultAxes(VirtualAxis.jvm)
  .jvmPlatform(
    scalaVersions = scalaVersions
  )
  .dependsOn(msgpack4zCore % "test->test")
