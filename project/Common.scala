import sbt._, Keys._
import sbtrelease._
import xerial.sbt.Sonatype._
import ReleaseStateTransformations._
import com.typesafe.sbt.pgp.PgpKeys
import sbtbuildinfo.Plugin._
import scalaprops.ScalapropsPlugin.autoImport._

object Common {

  final val ScalazVersion = "7.1.2"

  private def gitHash: String = scala.util.Try(
    sys.process.Process("git rev-parse HEAD").lines_!.head
  ).getOrElse("master")

  private[this] val unusedWarnings = (
    "-Ywarn-unused" ::
    "-Ywarn-unused-import" ::
    Nil
  )

  val settings = Seq(
    ReleasePlugin.releaseSettings,
    ReleasePlugin.extraReleaseCommands,
    sonatypeSettings,
    scalapropsWithScalazlaws,
    buildInfoSettings
  ).flatten ++ Seq(
    scalapropsVersion := "0.1.6",
    resolvers += Opts.resolver.sonatypeReleases,
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
    ReleasePlugin.ReleaseKeys.releaseProcess := Seq[ReleaseStep](
      ReleaseStep{ state =>
        assert(Sxr.disableSxr == false)
        state
      },
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
      commitNextVersion,
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
      Nil
    ) ::: unusedWarnings,
    scalaVersion := "2.11.6",
    crossScalaVersions := scalaVersion.value :: Nil,
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
