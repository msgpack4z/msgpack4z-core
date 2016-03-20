import sbt._
import org.scalajs.sbtplugin.cross.CrossType

/**
  * avoid move files
  * @see [[https://github.com/scala-js/scala-js/blob/v0.6.8/sbt-plugin/src/main/scala/scala/scalajs/sbtplugin/cross/CrossProject.scala#L193-L206]]
  */
object CustomCrossType extends CrossType {
  override def projectDir(crossBase: File, projectType: String) =
    crossBase / projectType

  def shared(projectBase: File, conf: String) =
    projectBase.getParentFile / "src" / conf / "scala"

  override def sharedSrcDir(projectBase: File, conf: String) =
    Some(shared(projectBase, conf))
}
