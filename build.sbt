import play.core.PlayVersion
import uk.gov.hmrc.DefaultBuildSettings.{addTestReportOption, defaultSettings, scalaSettings, targetJvm}
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin.publishingSettings

val appName = "request-corporation-tax-number-stubs"

lazy val appDependencies: Seq[ModuleID] = compile ++ test

lazy val scope: String = "test"
lazy val silencerVersion: String = "1.7.1"

val compile = Seq(
  ws,
  "uk.gov.hmrc" %% "bootstrap-backend-play-28" % "5.20.0",
  "uk.gov.hmrc" %% "domain" % "7.0.0-play-28",
  compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
  "com.github.ghik" % "silencer-lib"    % silencerVersion % Provided cross CrossVersion.full
)

def test: Seq[ModuleID] = Seq(
  "org.pegdown"            %  "pegdown"            % "1.6.0" % scope,
  "com.vladsch.flexmark"   %  "flexmark-all"       % "0.62.2" % scope,
  "com.typesafe.play"      %% "play-test"          % PlayVersion.current % scope,
  "org.scalatestplus"      %% "mockito-3-4"        % "3.2.10.0" % scope,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % scope
)



lazy val microservice = Project(appName, file("."))
  .enablePlugins(play.sbt.PlayScala, SbtDistributablesPlugin)
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
    PlayKeys.playDefaultPort := 9203,
  )
  .configs(IntegrationTest)
  .settings(inConfig(IntegrationTest)(Defaults.itSettings): _*)
  .settings(
    Keys.fork in IntegrationTest := false,
    addTestReportOption(IntegrationTest, "int-test-reports"),
    parallelExecution in IntegrationTest := false
  )
  .settings(majorVersion := 0)

scalacOptions ++= Seq(
  "-P:silencer:pathFilters=views;routes"
)