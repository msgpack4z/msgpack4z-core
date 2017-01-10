import build._

val scalapropsURI = uri("git://github.com/scalaprops/scalaprops.git#9e49b6ca7bd0da10a66b3706471fbe0611d42ab8")

lazy val msgpack4zJVM = msgpack4z.jvm.dependsOn(
  ProjectRef(scalapropsURI, "scalapropsJVM"),
  ProjectRef(scalapropsURI, "scalazlawsJVM")
)

lazy val msgpack4zJS = msgpack4z.js.dependsOn(
  ProjectRef(scalapropsURI, "scalapropsJS"),
  ProjectRef(scalapropsURI, "scalazlawsJS")
)

lazy val root = Project("root", file(".")).settings(
  Common.settings,
  noPublish,
  commands += Command.command("testSequential"){
    Seq(
      msgpack4zJVM, msgpack4zJS, testJava07, testJavaLatest
    ).map(_.id).map(_ + "/test").sorted ::: _
  },
  scalaSource in Compile := file("duumy"),
  scalaSource in Test := file("duumy")
).aggregate(
  msgpack4zJVM, msgpack4zJS, testJava07, testJavaLatest
)

lazy val testJava07 = Project("testJava07", file("test-java07")).settings(
  Common.settings,
  noPublish,
  libraryDependencies ++= (
    ("com.github.xuwei-k" % "msgpack4z-java07" % "0.2.0" % "test") ::
    Nil
  )
).dependsOn(msgpack4zJVM % "test->test")

lazy val testJavaLatest = Project("testJavaLatest", file("test-java-latest")).settings(
  Common.settings,
  noPublish,
  libraryDependencies ++= (
    ("com.github.xuwei-k" % "msgpack4z-java" % "0.3.5" % "test") ::
    Nil
  )
).dependsOn(msgpack4zJVM % "test->test")
