name := "jira-connector-v2"

version := "0.1"

scalaVersion := "2.12.10"

lazy val akkaHttpVersion = "10.1.11"

libraryDependencies ++= Seq(
"com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
"com.typesafe.akka" %% "akka-stream" % "2.5.26",
"com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
)