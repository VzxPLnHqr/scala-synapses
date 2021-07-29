val scala3Version = "3.0.1"

val circeVersion = "0.14.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "synapses",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scala-lang.modules" % "scala-parallel-collections_3" % "1.0.3",
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "com.novocode" % "junit-interface" % "0.11" % "test",
      "com.github.tototoshi" % "scala-csv_3" % "1.3.8" % "test"
    ),

    organization := "com.github.mrdimosthenis",

    homepage := Some(url("https://github.com/mrdimosthenis/Synapses")),

    scmInfo := Some(
      ScmInfo(url("https://github.com/mrdimosthenis/scala-synapses"),
        "git@github.com:mrdimosthenis/scala-synapses.git")
      ),

    developers := List(Developer(
      "mrdimosthenis",
      "Dimos Michailidis",
      "mrdimosthenis@hotmail.com",
      url("https://github.com/mrdimosthenis")
    )),

    licenses += ("MIT", url("https://rem.mit-license.org/")),

    publishMavenStyle := true,

    publishTo := Some(
      if (isSnapshot.value)
        Opts.resolver.sonatypeSnapshots
      else
        Opts.resolver.sonatypeStaging
    )
  )

// realease with sbt-release plugin
import ReleaseTransformations._
releaseCrossBuild := true
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies, // check that there are no SNAPSHOT dependencies
  inquireVersions, // ask user to enter the current and next verion
  runClean, // clean
  runTest, // run tests
  setReleaseVersion, // set release version in version.sbt
  commitReleaseVersion, // commit the release version
  tagRelease, // create git tag
  releaseStepCommandAndRemaining("+publishSigned"), // run +publishSigned command to sonatype stage release
  setNextVersion, // set next version in version.sbt
  commitNextVersion, // commint next version
  releaseStepCommand("sonatypeRelease"), // run sonatypeRelease and publish to maven central
  pushChanges // push changes to git
)
