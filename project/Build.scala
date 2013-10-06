import sbt._
import Keys._

object Build extends Build {

  val macroSettings = Project.defaultSettings ++ Seq(
    scalaVersion := "2.11.0-SNAPSHOT",
    resolvers += Resolver.sonatypeRepo("snapshots"),
    addCompilerPlugin("org.scala-lang.plugins" % "macro-paradise" % "2.0.0-SNAPSHOT" cross CrossVersion.full)
  )

  val dependencies = Seq(
    "org.scala-lang" % "scala-compiler" % "2.11.0-SNAPSHOT",
    "org.scala-lang" % "scala-library"  % "2.11.0-SNAPSHOT",
    "org.scalatest" % "scalatest_2.11.0-M5" % "2.0.M7" % "test"
  )

  val commonSettings = Project.defaultSettings ++ Seq(
    scalaVersion := "2.11.0-SNAPSHOT",
    resolvers += Resolver.sonatypeRepo("snapshots"),
    libraryDependencies := dependencies
  )

  lazy val macros = Project(
    id = "macros",
    base = file("macros"),
    settings = macroSettings ++ Seq(
      libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _),
      libraryDependencies ++= dependencies
    )
  )

  lazy val core: Project = Project(
    "core",
    file("core"),
    settings = commonSettings)
    .dependsOn(macros)

  lazy val root = Project(
    id = "root-project",
    base = file("."),
    settings = commonSettings)
    .aggregate(macros, core)
}
