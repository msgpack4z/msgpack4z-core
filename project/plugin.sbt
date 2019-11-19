addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.2")
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.12")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.9.0")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.8")
addSbtPlugin("com.github.scalaprops" % "sbt-scalaprops" % "0.3.2")
addSbtPlugin("com.typesafe" % "sbt-mima-plugin" % "0.6.1")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.30")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "0.6.1")
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % "0.6.1")
addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.3.9")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.2.1")

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-Yno-adapted-args",
)

fullResolvers ~= { _.filterNot(_.name == "jcenter") }
