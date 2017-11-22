name := "Templatey"

version := "0.1"

scalaVersion := "2.12.4"

val argonautVersion = "6.2"
val catsVersion = "1.0.0-RC1"
val catsEffectVersion = "0.5"
val circeVersion = "0.8.0"
val doobieVersion = "0.4.4"
val http4sVersion = "0.17.5"
val monocleVersionScalaz = "1.4.0"
val monocleVersionCats = "1.5.0-cats-M1"
val scalazVersion = "7.2.16"
val scalacheckVersion = "1.13.4"
val specsVersion = "4.0.0"

libraryDependencies ++= Seq(
  // Cats
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.typelevel" %% "dogs-core" % "0.6.9",

  // Http4s
  "org.http4s" %% "http4s-core" % http4sVersion,
  "org.http4s" %% "http4s-dsl" % http4sVersion,

  // Circe
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,

  // Apache POI (for writing word docs)
  "org.apache.poi" % "poi" % "3.17",

  // Generate pdfs from html
  "io.github.cloudify" %% "spdf" % "1.4.0",

  // Test
  "org.specs2" %% "specs2-core" % specsVersion % "test",
  "org.scalacheck" %% "scalacheck" % scalacheckVersion % "test"
)

// For macro annotations
addCompilerPlugin("org.scalamacros" %% "paradise" % "2.1.0" cross CrossVersion.full)