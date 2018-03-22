import sbt.Keys.libraryDependencies

lazy val commonSettings = Seq(
  scalaVersion := "2.12.4",
  organization := "play"
)
lazy val scalatest = "org.scalatest" %% "scalatest" % "3.0.5"
lazy val SerialTest = config("serial") extend Test

// Unit tests have Suffix Test, while tests that need to run Serially have suffix SerialTest
def serialTestFilter(name: String): Boolean = name endsWith "SerialTest"
def unitFilter(name: String): Boolean = (name endsWith "Test") && !serialTestFilter(name)

lazy val root = (project in file("."))
  .configs(SerialTest)
  .settings(
    commonSettings,
    name := "scala-playground",

    inConfig(SerialTest)(Defaults.testTasks),
    libraryDependencies += scalatest % "serial,test",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7",
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",

    testOptions in Test := Seq(Tests.Filter(unitFilter)),
    testOptions in SerialTest := Seq(Tests.Filter(serialTestFilter)),
    parallelExecution in SerialTest := false
  )