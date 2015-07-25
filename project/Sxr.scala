import sbt._, Keys._

object Sxr {

  val enableSxr = SettingKey[Boolean]("enableSxr")
  val sxr = TaskKey[File]("packageSxr")

  private[this] def ifSxrAvailable[A](key: SettingKey[A], value: Def.Initialize[A]): Setting[A] =
    key <<= (key, enableSxr, value){ (k, enable, vv) =>
      if (enable) {
        vv
      } else {
        k
      }
    }

  private[this] def ifSxrAvailable[A](key: TaskKey[A], value: Def.Initialize[Task[A]]): Setting[Task[A]] =
    key := {
      if (enableSxr.value) {
        value.value
      } else {
        key.value
      }
    }

  val settings: Seq[Setting[_]] = Defaults.packageTaskSettings(
    sxr in Compile, (crossTarget in Compile, compile in Compile).map{ (dir, _) =>
      Path.allSubpaths(dir / "classes.sxr").toSeq
    }
  ) ++ Seq[Setting[_]](
    enableSxr := { scalaVersion.value.startsWith("2.12") == false },
    ifSxrAvailable(
      resolvers,
      Def.setting(resolvers.value :+ ("bintray/paulp" at "https://dl.bintray.com/paulp/maven"))
    ),
    ifSxrAvailable(
      libraryDependencies,
      Def.setting(libraryDependencies.value :+ compilerPlugin("org.improving" %% "sxr" % "1.0.1"))
    ),
    ifSxrAvailable(
      packagedArtifacts,
      Def.task(packagedArtifacts.value ++ Classpaths.packaged(Seq(sxr in Compile)).value)
    ),
    ifSxrAvailable(
      artifacts,
      Def.setting(artifacts.value ++ Classpaths.artifactDefs(Seq(sxr in Compile)).value)
    ),
    ifSxrAvailable(
      artifactClassifier in sxr,
      Def.setting(Option("sxr"))
    ),
    ifSxrAvailable(
      scalacOptions in Compile,
      Def.task {
        (scalacOptions in Compile).value :+ (
          "-P:sxr:base-directory:" + (scalaSource in Compile).value.getAbsolutePath
        )
      }
    )
  )

}
