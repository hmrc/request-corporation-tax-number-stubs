import play.core.PlayVersion
import uk.gov.hmrc.DefaultBuildSettings.{addTestReportOption, defaultSettings, scalaSettings, targetJvm}
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin.publishingSettings

val appName = "request-corporation-tax-number-stubs"

lazy val appDependencies: Seq[ModuleID] = compile ++ test
lazy val plugins: Seq[Plugins] = Seq(play.sbt.PlayScala)
lazy val playSettings: Seq[Setting[_]] = Seq.empty

lazy val scope: String = "test"
lazy val silencerVersion: String = "1.7.1"

val compile = Seq(
  ws,
  "uk.gov.hmrc" %% "bootstrap-backend-play-27" % "4.0.0",
  "uk.gov.hmrc" %% "domain" % "5.10.0-play-27",
  compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
  "com.github.ghik" % "silencer-lib"    % silencerVersion % Provided cross CrossVersion.full
)

def test: Seq[ModuleID] = Seq(
  "org.scalatest"          %% "scalatest"          % "3.0.9" % scope,
  "org.pegdown"            %  "pegdown"            % "1.6.0" % scope,
  "com.typesafe.play"      %% "play-test"          % PlayVersion.current % scope,
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % scope,
  "org.mockito"            %  "mockito-core"       % "3.2.4" % scope
)



lazy val microservice = Project(appName, file("."))
  .enablePlugins(Seq(play.sbt.PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtDistributablesPlugin, SbtArtifactory) ++ plugins: _*)
  .settings(playSettings: _*)
  .settings(scalaSettings: _*)
  .settings(publishingSettings: _*)
  .settings(defaultSettings(): _*)
  .settings(
    scalaVersion := "2.12.12",
    targetJvm := "jvm-1.8",
    libraryDependencies ++= appDependencies,
    parallelExecution in Test := false,
    fork in Test := false,
    retrieveManaged := true,
    routesGenerator := InjectedRoutesGenerator,
    PlayKeys.devSettings += "play.server.http.port" -> "9203"
  )
  .configs(IntegrationTest)
  .settings(inConfig(IntegrationTest)(Defaults.itSettings): _*)
  .settings(
    Keys.fork in IntegrationTest := false,
    addTestReportOption(IntegrationTest, "int-test-reports"),
    parallelExecution in IntegrationTest := false
  )
  .settings(resolvers ++= Seq(
    Resolver.bintrayRepo("hmrc", "releases"),
    Resolver.jcenterRepo
  ))
  .settings(majorVersion := 0)

scalacOptions ++= Seq(
  "-P:silencer:pathFilters=views;routes"
)