# SBT-rundeck
Plugin defines necessary settings for developing and packaging Rundeck plugins according to [Rundeck guidelines](http://rundeck.org/docs/developer/plugin-development.html#java-plugin-development)

### Setup

For SBT 0.13.6+, add the following to your `project/plugins.sbt`:
```scala
resolvers += Resolver.url("sysa-bintray-repo", url("https://dl.bintray.com/sysa/sbt-plugins/"))(Resolver.ivyStylePatterns)
addSbtPlugin("io.sysa" % "sbt-rundeck" % "0.1.0")
```

### Usage

Enable plugin for your module and define requred `rundeckPluginClassnames` setting:
```scala
  lazy val root = project.in(file("."))
  .enablePlugins(RundeckPlugin)
    .settings(
      name := "rundeck-example-plugin",
      organization := "com.example",
      version := "1.0.0",
      scalaVersion := "2.11.7",
      libraryDependencies += Seq("..."),
      rundeckPluginClassnames := Seq("com.example.ExamplePluginEntrypoint")
    )
```

Plugin adds dependency for `rundeck-core` in `provided` scope, thus making it excluded during packaging. 
If version of this library don't satisfies your needs, you can specify another with `rundeckLibraryVersion` setting.

All `libraryDependencies` will be bundled within plugin jar in `lib` directory.

Each member of `rundeckPluginClassnames` should specify a valid Rundeck Provider class 
annotated with [`@Plugin`](http://rundeck.org/docs/javadoc/index.html?com/dtolabs/rundeck/core/plugins/Plugin.html)

To create plugin bundle, run: 

    sbt-console> package

Resulting plugin bundle will be located in `target/scala_2.11/rundeck-example-plugin-1.0.0.jar`
Follow [Plugin Installation](http://rundeck.org/docs/plugins-user-guide/installing.html) steps, 
to deploy your plugin to Rundeck instance.
