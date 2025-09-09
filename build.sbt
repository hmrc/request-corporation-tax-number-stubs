import uk.gov.hmrc.DefaultBuildSettings.{defaultSettings, scalaSettings}

val appName = "request-corporation-tax-number-stubs"

lazy val microservice = Project(appName, file("."))
  .enablePlugins(play.sbt.PlayScala, SbtDistributablesPlugin)
  .disablePlugins(JUnitXmlReportPlugin) //Required to prevent https://github.com/scalatest/scalatest/issues/1427
  .settings(scalaSettings)
  .settings(defaultSettings())
  .settings(CodeCoverageSettings())
  .settings(
    scalaVersion := "2.13.16",
    libraryDependencies ++= AppDependencies(),
    Test / parallelExecution := false,
    Test / fork := false,
    retrieveManaged := true,
    routesGenerator := InjectedRoutesGenerator,
    PlayKeys.playDefaultPort := 9203,
    majorVersion := 1,
    Test / parallelExecution := false,
    scalacOptions -= "-Xmax-classfile-name",
    scalacOptions += "-Wconf:cat=unused-imports&src=routes/.*:s"
  )
