lazy val sbtRundeck = Project("sbt-rundeck", file("."))
	.settings(
		name := "sbt-rundeck",
		version := "0.1.0",
		organization := "io.sysa",
		description := "Plugin defines necessary settings for developing and packaging Rundeck plugins",
		licenses += ("MIT", url("http://opensource.org/licenses/MIT")),

		sbtPlugin := true,
		scalaVersion := "2.10.5",
		publishMavenStyle := false,
		bintrayOrganization := Some("sysa"),
		bintrayReleaseOnPublish in ThisBuild := false
)

