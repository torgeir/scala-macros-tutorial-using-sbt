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

  val theSettings = Project.defaultSettings ++ Seq(
    organization := "gd.wa",
    version := "1.0.0",
    scalaVersion := "2.11.0-SNAPSHOT",
    scalacOptions := Seq(
      "-feature", // feature warnings
      "-language:reflectiveCalls"
    ),
    resolvers += Resolver.sonatypeRepo("snapshots"),
    libraryDependencies := dependencies
  )

  lazy val macros = Project("macros", file("macros"), settings = theSettings ++ macroSettings ++
    Seq(
      libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _),
      libraryDependencies ++= dependencies
    ))

  lazy val core = Project("core", file("core"), settings = theSettings)
    .dependsOn(macros)

  lazy val root = Project("root", file("."), settings = theSettings)
    .aggregate(macros, core)
}
