import uk.gov.hmrc.DefaultBuildSettings.{defaultSettings, scalaSettings}

val appName = "request-corporation-tax-number-stubs"

lazy val microservice = Project(appName, file("."))
  .enablePlugins(play.sbt.PlayScala, SbtDistributablesPlugin)
  .disablePlugins(JUnitXmlReportPlugin) //Required to prevent https://github.com/scalatest/scalatest/issues/1427
  .settings(scalaSettings)
  .settings(defaultSettings())
  .settings(
    scalaVersion := "2.13.16",
    libraryDependencies ++= AppDependencies(),
    Test / parallelExecution := false,
    Test / fork := false,
    retrieveManaged := true,
    routesGenerator := InjectedRoutesGenerator,
    PlayKeys.playDefaultPort := 9203,
    majorVersion := 1,
    coverageExcludedFiles := "<empty>;Reverse.*;.*filters.*;.*handlers.*;.*components.*;.*models.*;.*repositories.*;" +
      ".*BuildInfo.*;.*javascript.*;.*Routes.*;.*GuiceInjector;.*.template.scala;",
    coverageMinimumBranchTotal := 80,
    coverageMinimumStmtTotal := 80,
    coverageMinimumStmtPerPackage := 80,
    coverageFailOnMinimum := true,
    coverageHighlighting := true,
    Test / parallelExecution := false,
    scalacOptions -= "-Xmax-classfile-name",
    scalacOptions += "-Wconf:cat=unused-imports&src=routes/.*:s"
  )

addCommandAlias("scalastyleAll", "all scalastyle Test/scalastyle")
