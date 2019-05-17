import build._
import sbtrelease.ReleaseStateTransformations._
import com.typesafe.sbt.pgp.PgpKeys
import sbtcrossproject.CrossProject

val msgpack4zNativeVersion = "0.3.5"
val scalapropsVersion = "0.6.0"
def ScalazVersion = "7.2.27"
def Scala211 = "2.11.12"

val tagName = Def.setting {
  s"v${if (releaseUseGlobalVersion.value) (version in ThisBuild).value else version.value}"
}
val tagOrHash = Def.setting {
  if (isSnapshot.value) gitHash() else tagName.value
}

val SetScala211 = releaseStepCommand("++" + Scala211)

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
    s"""\nbuild.${build.mimaBasis.key.label} in ThisBuild := \"${releaseV}\"\n""",
    append = true
  )
  reapply(Seq(build.mimaBasis in ThisBuild := releaseV), st)
}

val commonSettings = Def.settings(
  ReleasePlugin.extraReleaseCommands,
  scalapropsCoreSettings,
  publishTo := Some(
    if (isSnapshot.value)
      Opts.resolver.sonatypeSnapshots
    else
      Opts.resolver.sonatypeStaging
  ),
  resolvers += Opts.resolver.sonatypeReleases,
  fullResolvers ~= { _.filterNot(_.name == "jcenter") },
  scalaModuleInfo ~= (_.map(_.withOverrideScalaVersion(true))),
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
        extracted.runAggregated(PgpKeys.publishSigned in Global in extracted.get(thisProjectRef), state)
      },
      enableCrossBuild = true
    ),
    SetScala211,
    releaseStepCommand(build.msgpack4zCoreName + "Native/publishSigned"),
    setNextVersion,
    setMimaVersion,
    commitNextVersion,
    releaseStepCommand("sonatypeReleaseAll"),
    UpdateReadme.updateReadmeProcess,
    pushChanges
  ),
  credentials ++= PartialFunction
    .condOpt(sys.env.get("SONATYPE_USER") -> sys.env.get("SONATYPE_PASS")) {
      case (Some(user), Some(pass)) =>
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
    "-Xfuture",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
  ),
  scalacOptions ++= unusedWarnings,
  scalacOptions ++= PartialFunction
    .condOpt(CrossVersion.partialVersion(scalaVersion.value)) {
      case Some((2, v)) if v <= 12 =>
        "-Yno-adapted-args"
    }
    .toList,
  scalaVersion := Scala211,
  crossScalaVersions := Scala211 :: "2.12.8" :: "2.13.0-RC2" :: Nil,
  scalacOptions in (Compile, doc) ++= {
    val tag = tagOrHash.value
    Seq(
      "-sourcepath",
      (baseDirectory in LocalRootProject).value.getAbsolutePath,
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
    def stripIf(f: Node => Boolean) = new RewriteRule {
      override def transform(n: Node) =
        if (f(n)) NodeSeq.Empty else n
    }
    val stripTestScope = stripIf { n =>
      n.label == "dependency" && (n \ "scope").text == "test"
    }
    new RuleTransformer(stripTestScope).transform(node)(0)
  }
) ++ Seq(Compile, Test).flatMap(c => scalacOptions in (c, console) --= unusedWarnings)

lazy val msgpack4zCore = CrossProject(
  id = msgpack4zCoreName,
  base = file(".")
)(
  JSPlatform,
  JVMPlatform,
  NativePlatform
).crossType(
    CustomCrossType
  )
  .settings(
    MimaPlugin.mimaDefaultSettings,
    commonSettings,
    Generator.settings,
    name := msgpack4zCoreName,
    libraryDependencies ++= Seq(
      "org.scalaz" %%% "scalaz-core" % ScalazVersion,
      "com.github.xuwei-k" %% "zeroapply-scalaz" % "0.2.2" % "provided",
      "com.github.scalaprops" %%% "scalaprops" % scalapropsVersion % "test",
      "com.github.scalaprops" %%% "scalaprops-scalaz" % scalapropsVersion % "test",
    )
  )
  .enablePlugins(
    sbtbuildinfo.BuildInfoPlugin
  )
  .jvmSettings(
    Sxr.settings,
    libraryDependencies ++= Seq(
      "com.github.xuwei-k" % "msgpack4z-api" % "0.2.0",
      "com.github.xuwei-k" % "msgpack4z-java06" % "0.2.0" % "test",
      "com.github.xuwei-k" %% "msgpack4z-native" % msgpack4zNativeVersion % "test",
    )
  )
  .jsSettings(
    scalacOptions += {
      val a = (baseDirectory in LocalRootProject).value.toURI.toString
      val g = "https://raw.githubusercontent.com/msgpack4z/msgpack4z-core/" + tagOrHash.value
      s"-P:scalajs:mapSourceURI:$a->$g/"
    },
    scalaJSSemantics ~= { _.withStrictFloats(true) }
  )
  .platformsSettings(NativePlatform, JSPlatform)(
    libraryDependencies ++= Seq(
      "com.github.xuwei-k" %%% "msgpack4z-native" % msgpack4zNativeVersion,
    )
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

lazy val msgpack4zCoreJVM = msgpack4zCore.jvm
lazy val msgpack4zCoreJS = msgpack4zCore.js
lazy val msgpack4zCoreNative = msgpack4zCore.native.settings(
  scalapropsNativeSettings
)

lazy val root = Project("root", file("."))
  .settings(
    commonSettings,
    noPublish,
    commands += Command.command("testSequential") {
      List(
        msgpack4zCoreJVM,
        msgpack4zCoreJS,
        testJava07,
        testJavaLatest
      ).map(_.id).map(_ + "/test").sorted ::: _
    },
    scalaSource in Compile := file("duumy"),
    scalaSource in Test := file("duumy")
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
      ("com.github.xuwei-k" % "msgpack4z-java" % "0.3.5" % "test"),
    )
  )
  .dependsOn(msgpack4zCoreJVM % "test->test")
