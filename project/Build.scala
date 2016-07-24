import sbt._
import Keys._

object BuildSettings {
  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "com.softwaremill.scala-macro-aop",
    version := "1.0.0",
    scalacOptions ++= Seq(),
    scalaVersion := "2.11.8",
    resolvers += Resolver.sonatypeRepo("snapshots"),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
  )
}

object MyBuild extends Build {
  import BuildSettings._

  lazy val root: Project = Project(
    "root",
    file("."),
    settings = buildSettings ++ Seq(
      run <<= run in Compile in examples
    )
  ) aggregate(macros, examples)

  lazy val macros: Project = Project(
    "macros",
    file("macros"),
    settings = buildSettings ++ Seq(
      libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _))
  )

  lazy val examples: Project = Project(
    "examples",
    file("examples"),
    settings = buildSettings
  ) dependsOn(macros)
}
