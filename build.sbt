// build.sbt:

organization := "us.aguilera"
organizationName := "Jeffrey Aguilera"
organizationHomepage := Some(new URL("https://github.com/jeffrey-aguilera"))

name := "josephus"
homepage := Some(new URL("https://github.com/jeffrey-aguilera/josephus"))
startYear := Some(2017)
version := "0.0.1"

scalaVersion := "2.12.1"
scalacOptions ++= Seq(
  "-encoding", "UTF-8",
  "-explaintypes",
  "-feature",
  "-language:_",
  "-optimize",
  "-target:jvm-1.8",
  "-unchecked",
  "-Xlint:_,-package-object-classes",
  "-Yinline-warnings",
  "-Ywarn-dead-code",
  "-Ywarn-inaccessible",
  "-Ywarn-nullary-override",
  "-Ywarn-nullary-unit",
  //"-Ywarn-unused",
  "-Ywarn-unused-import")

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "3.8.9" % Test
)

// see: https://etorreborre.github.io/specs2/guide/SPECS2-3.8.9/org.specs2.guide.Installation.html#other-dependencies
scalacOptions in Test ++= Seq(
  "-Yrangepos")

initialCommands := "import us.aguilera.josephus._"
