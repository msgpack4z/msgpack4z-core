addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.0")
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.5")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.7.0")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "2.0")
addSbtPlugin("com.github.scalaprops" % "sbt-scalaprops" % "0.2.4")
addSbtPlugin("com.typesafe" % "sbt-mima-plugin" % "0.1.18")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.21")
addSbtPlugin("org.scala-native" % "sbt-scalajs-crossproject" % "0.2.2")
addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.3.6")

scalacOptions ++= (
  "-deprecation" ::
  "-unchecked" ::
  "-Xlint" ::
  "-language:existentials" ::
  "-language:higherKinds" ::
  "-language:implicitConversions" ::
  "-Yno-adapted-args" ::
  Nil
)

fullResolvers ~= {_.filterNot(_.name == "jcenter")}
