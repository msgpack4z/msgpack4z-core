import build._

lazy val msgpack4zCoreJVM = msgpack4zCore.jvm
lazy val msgpack4zCoreJS = msgpack4zCore.js
lazy val msgpack4zCoreNative = msgpack4zCore.native.settings(
  scalapropsNativeSettings
)

lazy val root = Project("root", file(".")).settings(
  Common.settings,
  noPublish,
  commands += Command.command("testSequential"){
    List(
      msgpack4zCoreJVM, msgpack4zCoreJS, testJava07, testJavaLatest
    ).map(_.id).map(_ + "/test").sorted ::: _
  },
  scalaSource in Compile := file("duumy"),
  scalaSource in Test := file("duumy")
).aggregate(
  msgpack4zCoreJVM, msgpack4zCoreJS, testJava07, testJavaLatest
)

lazy val testJava07 = Project("testJava07", file("test-java07")).settings(
  Common.settings,
  noPublish,
  libraryDependencies ++= (
    ("com.github.xuwei-k" % "msgpack4z-java07" % "0.2.0" % "test") ::
    Nil
  )
).dependsOn(msgpack4zCoreJVM % "test->test")

lazy val testJavaLatest = Project("testJavaLatest", file("test-java-latest")).settings(
  Common.settings,
  noPublish,
  libraryDependencies ++= (
    ("com.github.xuwei-k" % "msgpack4z-java" % "0.3.5" % "test") ::
    Nil
  )
).dependsOn(msgpack4zCoreJVM % "test->test")
