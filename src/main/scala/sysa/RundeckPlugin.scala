package sysa

import sbt._
import Keys._

object RundeckPlugin extends AutoPlugin {

	override def requires = sbt.plugins.JvmPlugin
	override def trigger = noTrigger

	override lazy val projectSettings: Seq[Setting[_]] = baseSettings

	object autoImport {
		lazy val rundeckLibraryVersion = settingKey[String]("Version of rundeck core dependency")
		lazy val rundeckPluginClassnames = settingKey[Seq[String]]("List of plugin's main classes to mention in manifest")
	}

	import autoImport._

	lazy val baseSettings: Seq[Setting[_]] = Seq(
		rundeckLibraryVersion := "2.5.3",
		libraryDependencies += "org.rundeck" % "rundeck-core" % rundeckLibraryVersion.value % "provided",

		packageOptions in (Compile, packageBin) := Seq(Package.ManifestAttributes(
			"Rundeck-Plugin-Version" -> "1.1",
			"Rundeck-Plugin-Archive" -> "true",
			"Rundeck-Plugin-File-Version" -> version.value,
			"Rundeck-Plugin-Classnames" -> rundeckPluginClassnames.value.mkString(","),
			"Rundeck-Plugin-Libs" -> Attributed.data((externalDependencyClasspath in Runtime).value).map( dep => s"lib/${dep.name}" ).mkString(" ")
		)),

		mappings in (Compile, packageBin) ++= {
			val pluginDeps = Attributed.data((externalDependencyClasspath in Runtime).value)
			pluginDeps pair flatRebase("lib")
		},

		publishArtifact in (Compile, packageDoc) := false,
		publishArtifact in (Compile, packageSrc) := false,
		publishArtifact in makePom := false,

		artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
			artifact.name + "-" + module.revision + "." + artifact.extension
		}
	)
}
