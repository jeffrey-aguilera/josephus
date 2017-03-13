// build.sbt:

name := "josephus"
organization := "us.aguilera"
version := "0.0.1"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "3.8.9" % Test
)

// see: https://etorreborre.github.io/specs2/guide/SPECS2-3.8.3/org.specs2.guide.Installation.html#other-dependencies
scalacOptions in Test ++= Seq("-Yrangepos")

initialCommands := "import us.aguilera.josephus._"
