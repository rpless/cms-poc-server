
lazy val root = project.in(file("."))
  .settings(moduleName := "cms-poc")
  .settings(noPublish)
  .aggregate(webserver)

lazy val domain = project
  .settings(moduleName := "cms-poc-domain")
  .settings(baseSettings)
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats" % "0.9.0",
      "com.twitter" %% "util-core" % "6.40.0"
    )
  )

lazy val webserver = project
  .settings(moduleName := "cms-poc-webserver")
  .settings(baseSettings)
  .settings(
    libraryDependencies ++= Seq(
      "com.github.finagle" %% "finch-core" % "0.12.0",
      "com.github.finagle" %% "finch-circe" % "0.12.0",
      "io.circe" %% "circe-generic" % "0.6.1",
      "io.catbird" %% "catbird-util" % "0.11.0",

      "org.sangria-graphql" %% "sangria" % "1.0.0",
      "org.sangria-graphql" %% "sangria-circe" % "1.0.0"
    )
  )
  .dependsOn(domain)

lazy val baseSettings = Seq(
  scalaVersion := "2.12.1",
  scalacOptions ++= compilerOptions
)

lazy val compilerOptions = Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Xfuture",
  "-Xlint"
)

lazy val noPublish = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false
)
