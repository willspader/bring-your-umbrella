name := "bring-your-umbrella"

version := "0.1"

scalaVersion := "2.12.7"

val logbackClassicVersion = "0.9.24"
val sttpVersion = "2.2.4"
val json4sVersion = "3.6.0"
val reactiveMongoDBVersion = "0.16.0"

libraryDependencies ++= Seq(
  "com.softwaremill.sttp.client" %% "core" % sttpVersion,
  "com.softwaremill.sttp.client" %% "json4s" % sttpVersion,
  "org.json4s" %% "json4s-native" % json4sVersion,
  "com.enragedginger" %% "akka-quartz-scheduler" % "1.8.0-akka-2.5.x",
  "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
  "org.reactivemongo" %% "reactivemongo" % reactiveMongoDBVersion
)
