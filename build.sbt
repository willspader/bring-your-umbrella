name := "bring-your-umbrella"

version := "0.1"

scalaVersion := "2.12.7"

val akkaActorVersion = "2.5.21"
val akkaHttpVersion = "10.2.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaActorVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.enragedginger" %% "akka-quartz-scheduler" % "1.8.0-akka-2.5.x",
  "ch.qos.logback" % "logback-classic" % "0.9.24"
)
