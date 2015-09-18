import sbt._, Keys._
import xerial.sbt.Sonatype._
import sbtrelease._
import sbtrelease.ReleasePlugin.autoImport._
import ReleaseStateTransformations._
import com.typesafe.sbt.pgp.PgpKeys
import com.typesafe.tools.mima.plugin.MimaKeys.previousArtifact
import sbtbuildinfo.Plugin._
import scalaprops.ScalapropsPlugin.autoImport._

object Common {

  def ScalazVersion = "7.1.4"

  private[this] def Scala211 = "2.11.7"

  private def gitHash: String = scala.util.Try(
    sys.process.Process("git rev-parse HEAD").lines_!.head
  ).getOrElse("master")

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
    scalapropsVersion := "0.1.12",
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
    previousArtifact := {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, v)) if v >= 12 =>
          None
        case _ =>
          build.mimaBasis.?.value.map { v =>
            (organization.value % (name.value + "_" + scalaBinaryVersion.value) % v)
          }
      }
    },
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
    crossScalaVersions := Scala211 :: "2.12.0-M2" :: Nil,
    scalacOptions in (Compile, doc) ++= {
      val tag = if(isSnapshot.value) gitHash else { "v" + version.value }
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
