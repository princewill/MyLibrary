name := "my-library"
organization := "com.interview"

version := "1.0-SNAPSHOT"

lazy val postgresVersion = "42.1.4"
lazy val slickVersion = "5.0.0"
lazy val h2Version = "1.4.196"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  guice,
  filters,
  "org.scalatestplus.play"     %% "scalatestplus-play"        % "5.0.0"           % Test,

  // slick SQL generator
  "com.typesafe.play"          %% "play-slick"                % slickVersion,
  "com.typesafe.play"          %% "play-slick-evolutions"     % slickVersion,

  // H2 in-memory driver
  "com.h2database"             % "h2"                         % h2Version,

  // TEST
  //"org.scalatest"              %% "scalatest"                 % "3.0.5"          % Test,
  //"org.mockito"                % "mockito-core"               % "1.9.5"          % Test,

  //specs2                                                                         % Test,

)


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.interview.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.interview.binders._"
