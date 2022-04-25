name := "SummerExp"

version := "0.1"

scalaVersion := "2.13.8"
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "3.2.1",
  "org.apache.spark" %% "spark-core" % "3.2.1",
  "com.softwaremill.sttp.client3" %% "core" % "3.5.2",
  "org.scalatest" %% "scalatest" % "3.2.11" % "test"
)