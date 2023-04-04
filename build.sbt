import play.core.PlayVersion
import uk.gov.hmrc.DefaultBuildSettings.{addTestReportOption, defaultSettings, scalaSettings, targetJvm}

val appName = "request-corporation-tax-number-stubs"

lazy val appDependencies: Seq[ModuleID] = compile ++ test

lazy val scope: String = "test"
val bootstrapVersion = "7.14.0"

val compile = Seq(
  ws,
  "uk.gov.hmrc" %% "bootstrap-backend-play-28" % bootstrapVersion,
  "uk.gov.hmrc" %% "domain" % "8.1.0-play-28",
)

def test: Seq[ModuleID] = Seq(

  "org.pegdown"            %  "pegdown"                 % "1.6.0" % scope,
  "com.vladsch.flexmark"   %  "flexmark-all"            % "0.62.2" % scope,
  "com.typesafe.play"      %% "play-test"               % PlayVersion.current % scope,
  "org.scalatestplus"      %% "mockito-3-4"             % "3.2.10.0" % scope,
  "org.scalatestplus.play" %% "scalatestplus-play"      % "5.1.0" % scope,
  "uk.gov.hmrc"            %% "bootstrap-test-play-28"  % bootstrapVersion % scope
)



lazy val microservice = Project(appName, file("."))
  .enablePlugins(play.sbt.PlayScala, SbtDistributablesPlugin)
  .settings(scalaSettings: _*)
  .settings(defaultSettings(): _*)
  .settings(
    scalaVersion := "2.13.10",
    targetJvm := "jvm-1.8",
    libraryDependencies ++= appDependencies,
    Test / parallelExecution := false,
    Test / fork := false,
    retrieveManaged := true,
    routesGenerator := InjectedRoutesGenerator,
    PlayKeys.playDefaultPort := 9203
  )
  .configs(IntegrationTest)
  .settings(inConfig(IntegrationTest)(Defaults.itSettings): _*)
  .settings(
    coverageExcludedFiles := "<empty>;Reverse.*;.*filters.*;.*handlers.*;.*components.*;.*models.*;.*repositories.*;" +
      ".*BuildInfo.*;.*javascript.*;.*Routes.*;.*GuiceInjector;.*.template.scala;",
    coverageMinimumBranchTotal := 80,
    coverageMinimumStmtTotal := 80,
    coverageMinimumStmtPerPackage := 80,
    coverageFailOnMinimum := true,
    coverageHighlighting := true,
    Test / parallelExecution := false,
    libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
  )
  .configs(IntegrationTest)
  .settings(inConfig(IntegrationTest)(Defaults.itSettings): _*)
  .settings(
    scalacOptions -= "-Xmax-classfile-name",
    scalacOptions ++= Seq(
      "-Wconf:src=routes/.*:s",
      "-Wconf:cat=unused-imports&src=html/.*:s",
      "-Wconf:cat=unused-imports&src=xml/.*:s"
    )
  )
  .settings(
    IntegrationTest / Keys.fork  := false,
    IntegrationTest / unmanagedSourceDirectories := (IntegrationTest / baseDirectory)(base => Seq(base / "it")).value,
    addTestReportOption(IntegrationTest, "int-test-reports"),
    IntegrationTest / parallelExecution := false)
  .settings(majorVersion := 1)
