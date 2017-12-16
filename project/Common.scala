import sbt._, Keys._
import xerial.sbt.Sonatype._
import sbtrelease._
import sbtrelease.ReleasePlugin.autoImport._
import ReleaseStateTransformations._
import com.typesafe.sbt.pgp.PgpKeys
import com.typesafe.tools.mima.plugin.MimaKeys.mimaPreviousArtifacts
import sbtbuildinfo.BuildInfoPlugin.autoImport._
import scalaprops.ScalapropsPlugin.autoImport._

object Common {

  val tagName = Def.setting{
    s"v${if (releaseUseGlobalVersion.value) (version in ThisBuild).value else version.value}"
  }
  val tagOrHash = Def.setting{
    if(isSnapshot.value) gitHash() else tagName.value
  }

  def ScalazVersion = "7.2.17"

  private[this] def Scala211 = "2.11.11"

  private[this] val SetScala211 = releaseStepCommand("++" + Scala211)

  private def gitHash(): String =
    sys.process.Process("git rev-parse HEAD").lineStream_!.head

  private[this] val unusedWarnings = (
    "-Ywarn-unused" ::
    "-Ywarn-unused-import" ::
    Nil
  )

  private[this] val setMimaVersion: State => State = { st =>
    val extracted = Project.extract(st)
    val (releaseV, _) = st.get(ReleaseKeys.versions).getOrElse(sys.error("impossible"))
    IO.write(
      extracted get releaseVersionFile,
      s"""\nbuild.${build.mimaBasis.key.label} in ThisBuild := \"${releaseV}\"\n""",
      append = true
    )
    reapply(Seq(build.mimaBasis in ThisBuild := releaseV), st)
  }

  val settings = Seq(
    ReleasePlugin.extraReleaseCommands,
    sonatypeSettings,
    scalapropsCoreSettings
  ).flatten ++ Seq(
    publishTo := Some(
      if (isSnapshot.value)
        Opts.resolver.sonatypeSnapshots
      else
        Opts.resolver.sonatypeStaging
    ),
    resolvers += Opts.resolver.sonatypeReleases,
    fullResolvers ~= {_.filterNot(_.name == "jcenter")},
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
      SetScala211,
      releaseStepCommand(build.msgpack4zCoreName + "Native/test"),
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
    credentials ++= PartialFunction.condOpt(sys.env.get("SONATYPE_USER") -> sys.env.get("SONATYPE_PASS")){
      case (Some(user), Some(pass)) =>
        Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", user, pass)
    }.toList,
    organization := "com.github.xuwei-k",
    homepage := Some(url("https://github.com/msgpack4z")),
    licenses := Seq("MIT License" -> url("http://www.opensource.org/licenses/mit-license.php")),
    scalacOptions ++= (
      "-target:jvm-1.8" ::
      "-deprecation" ::
      "-unchecked" ::
      "-Xlint" ::
      "-Xfuture" ::
      "-language:existentials" ::
      "-language:higherKinds" ::
      "-language:implicitConversions" ::
      "-Yno-adapted-args" ::
      Nil
    ) ::: unusedWarnings,
    scalaVersion := Scala211,
    crossScalaVersions := Scala211 :: "2.12.4" :: "2.13.0-M2" :: Nil,
    scalacOptions in (Compile, doc) ++= {
      val tag = tagOrHash.value
      Seq(
        "-sourcepath", (baseDirectory in LocalRootProject).value.getAbsolutePath,
        "-doc-source-url", s"https://github.com/msgpack4z/msgpack4z-core/tree/${tag}€{FILE_PATH}.scala"
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
      </scm>
    ,
    description := "typeclass based msgpack serializer/deserializer",
    pomPostProcess := { node =>
      import scala.xml._
      import scala.xml.transform._
      def stripIf(f: Node => Boolean) = new RewriteRule {
        override def transform(n: Node) =
          if (f(n)) NodeSeq.Empty else n
      }
      val stripTestScope = stripIf { n => n.label == "dependency" && (n \ "scope").text == "test" }
      new RuleTransformer(stripTestScope).transform(node)(0)
    }
  ) ++ Seq(Compile, Test).flatMap(c =>
    scalacOptions in (c, console) --= unusedWarnings
  )

}
