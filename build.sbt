enablePlugins(JavaAppPackaging)

name := "html-sanitizing"
version := "1.0"
scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV       = "2.4.12"
  val akkaHttpV   = "3.0.0-RC1"
  val scalaTestV  = "3.0.1"
  val htmlSanV    = "20160924.1"
  val guavaV      = "19.0"
  val jsr305V     = "3.0.1"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-testkit" % akkaV,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV,
    "org.scalatest"     %% "scalatest" % scalaTestV % "test",

    "com.google.guava" % "guava" % guavaV,
    "com.google.code.findbugs"   % "jsr305" % jsr305V % "compile",
    "com.googlecode.owasp-java-html-sanitizer" % "owasp-java-html-sanitizer" % htmlSanV

  )
}

Revolver.settings
