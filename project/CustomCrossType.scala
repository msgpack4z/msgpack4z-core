import sbt._
import scalanativecrossproject.ScalaNativeCrossPlugin.autoImport._
import scalajscrossproject.ScalaJSCrossPlugin.autoImport._
import sbtcrossproject.CrossPlugin.autoImport._

/**
  * avoid move files
  */
object CustomCrossType extends sbtcrossproject.CrossType {
  override def projectDir(crossBase: File, projectType: String) =
    crossBase / projectType

  override def projectDir(crossBase: File, projectType: sbtcrossproject.Platform) = {
    val dir = projectType match {
      case JVMPlatform => "jvm"
      case JSPlatform => "js"
      case NativePlatform => "native"
    }
    crossBase / dir
  }

  def shared(projectBase: File, conf: String) =
    projectBase.getParentFile / "src" / conf / "scala"

  override def sharedSrcDir(projectBase: File, conf: String) =
    Some(shared(projectBase, conf))
}
