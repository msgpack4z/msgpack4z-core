import build._

lazy val msgpack4zJVM = msgpack4z.jvm
lazy val msgpack4zJS = msgpack4z.js

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
    ("com.github.xuwei-k" % "msgpack4z-java" % "0.3.4" % "test") ::
    Nil
  )
).dependsOn(msgpack4zJVM % "test->test")
