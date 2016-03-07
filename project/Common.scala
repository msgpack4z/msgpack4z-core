import sbt._, Keys._
import xerial.sbt.Sonatype._
import sbtrelease._
import sbtrelease.ReleasePlugin.autoImport._
import ReleaseStateTransformations._
import com.typesafe.sbt.pgp.PgpKeys
import com.typesafe.tools.mima.plugin.MimaKeys.previousArtifacts
import sbtbuildinfo.Plugin._
import scalaprops.ScalapropsPlugin.autoImport._

object Common {

  val tagName = Def.setting{
    s"v${if (releaseUseGlobalVersion.value) (version in ThisBuild).value else version.value}"
  }
  val tagOrHash = Def.setting{
    if(isSnapshot.value) gitHash() else tagName.value
  }

  def ScalazVersion = "7.2.1"

  private[this] def Scala211 = "2.11.7"

  private def gitHash(): String =
    sys.process.Process("git rev-parse HEAD").lines_!.head

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
      s"""\n\n${build.mimaBasis.key.label} in ThisBuild := \"${releaseV}\"\n""",
      append = true
    )
    reapply(Seq(build.mimaBasis in ThisBuild := releaseV), st)
  }

  val settings = Seq(
    ReleasePlugin.extraReleaseCommands,
    sonatypeSettings,
    scalapropsWithScalazlaws,
    buildInfoSettings
  ).flatten ++ Seq(
    scalapropsVersion := "0.2.1",
    resolvers += Opts.resolver.sonatypeReleases,
    fullResolvers ~= {_.filterNot(_.name == "jcenter")},
    ivyScala ~= { _.map(_.copy(overrideScalaVersion = true)) },
    buildInfoKeys := Seq[BuildInfoKey](
      organization,
      name,
      version,
      scalaVersion,
      sbtVersion,
      scalacOptions,
      licenses,
      "scalazVersion" -> ScalazVersion
    ),
    buildInfoPackage := "msgpack4z",
    buildInfoObject := "BuildInfoMsgpack4zCore",
    sourceGenerators in Compile <+= buildInfo,
    commands += Command.command("updateReadme")(UpdateReadme.updateReadmeTask),
    commands += Command.command("setMimaVersion")(setMimaVersion),
    previousArtifacts := {
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
      ReleaseStep(state => Project.extract(state).runTask(PgpKeys.publishSigned, state)._1),
      setNextVersion,
      setMimaVersion,
      commitNextVersion,
      ReleaseStep(state =>
        Project.extract(state).runTask(SonatypeKeys.sonatypeReleaseAll, state)._1
      ),
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
      "-deprecation" ::
      "-unchecked" ::
      "-Xlint" ::
      "-language:existentials" ::
      "-language:higherKinds" ::
      "-language:implicitConversions" ::
      "-Yno-adapted-args" ::
      Nil
    ) ::: unusedWarnings,
    scalaVersion := Scala211,
    crossScalaVersions := Scala211 :: Nil, // "2.12.0-M3" https://issues.scala-lang.org/browse/SI-9546
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
        <tag>{if(isSnapshot.value) gitHash else { "v" + version.value }}</tag>
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
    scalacOptions in (c, console) ~= {_.filterNot(unusedWarnings.toSet)}
  )

}
