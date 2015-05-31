addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.1")
addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.8.5")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.3.2")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "0.4.0")
addSbtPlugin("com.github.scalaprops" % "sbt-scalaprops" % "0.1.0")

scalacOptions ++= (
  "-deprecation" ::
  "-unchecked" ::
  "-Xlint" ::
  "-optimize" ::
  "-language:existentials" ::
  "-language:higherKinds" ::
  "-language:implicitConversions" ::
  Nil
)
