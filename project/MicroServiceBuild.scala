import sbt._

object MicroServiceBuild extends Build with MicroService {
  import scala.util.Properties.envOrElse

  val appName = "request-corporation-tax-number-stubs"
  val appVersion = envOrElse("REQUEST_CORPORATION_TAX_NUMBER_STUBS_VERSION", "999-SNAPSHOT")

  override lazy val appDependencies: Seq[ModuleID] = AppDependencies()
}

private object AppDependencies {
  import play.sbt.PlayImport._

  val compile = Seq(
    filters,
    ws,
    "uk.gov.hmrc" %% "microservice-bootstrap" % "6.10.0",
    "uk.gov.hmrc" %% "hmrc-stubs-core" % "5.2.0"
  )

  val test = Seq(
    "uk.gov.hmrc" %% "hmrctest" % "2.3.0" % "test,it",
    "org.scalatest" %% "scalatest" % "2.2.2" % "test,it",
    "org.pegdown" % "pegdown" % "1.4.2" % "test,it",
    "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % "test,it",
    "org.mockito" % "mockito-all" % "1.9.5" % "test,it"
  )

  def apply() = compile ++ test
}
