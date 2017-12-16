import sbt._, Keys._

object Sxr {

  val enableSxr = SettingKey[Boolean]("enableSxr")
  val sxr = TaskKey[File]("packageSxr")

  private[this] def ifSxrAvailable[A](key: SettingKey[A], value: Def.Initialize[A]): Setting[A] =
    key := {
      if (enableSxr.value) {
        value.value
      } else {
        key.value
      }
    }

  private[this] def ifSxrAvailable[A](key: TaskKey[A], value: Def.Initialize[Task[A]]): Setting[Task[A]] =
    key := {
      if (enableSxr.value) {
        value.value : @sbtUnchecked
      } else {
        key.value : @sbtUnchecked
      }
    }

  val settings: Seq[Setting[_]] = Defaults.packageTaskSettings(
    sxr in Compile,
    Def.task{
      val dir = (crossTarget in Compile).value
      val _ = (compile in Compile).value
      Path.allSubpaths(dir / "classes.sxr").toSeq
    }
  ) ++ Seq[Setting[_]](
    enableSxr := {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, v)) =>
          v <= 12
        case _ =>
          false
      }
    },
    ifSxrAvailable(
      resolvers,
      Def.setting(resolvers.value :+ ("bintray/paulp" at "https://dl.bintray.com/paulp/maven"))
    ),
    ifSxrAvailable(
      libraryDependencies,
      Def.setting(libraryDependencies.value :+ compilerPlugin("org.improving" %% "sxr" % "1.0.2"))
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
