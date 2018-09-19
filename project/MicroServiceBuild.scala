import play.core.PlayVersion
import sbt._

object MicroServiceBuild extends Build with MicroService {

  val appName = "request-corporation-tax-number-stubs"

  override lazy val appDependencies: Seq[ModuleID] = AppDependencies()
}

private object AppDependencies {
  import play.sbt.PlayImport._

  private val scalaTestPlusPlayVersion = "2.0.1"
  lazy val scope: String = "test"
  private val mockitoAllVersion = "1.10.19"
  private val domainVersion = "5.2.0"

  val compile = Seq(

    ws,
    "uk.gov.hmrc" %% "bootstrap-play-25" % "3.3.0",
    "uk.gov.hmrc" %% "domain" % domainVersion
  )

  val test = Seq(
    "uk.gov.hmrc" %% "hmrctest" % "3.0.0" % "test,it",
    "org.scalatest" %% "scalatest" % "2.2.6" % "test,it",
    "org.pegdown" % "pegdown" % "1.4.2" % "test,it",
    "com.typesafe.play" %% "play-test" % PlayVersion.current % "test,it",
    "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestPlusPlayVersion % scope,
    "org.mockito" % "mockito-all" % mockitoAllVersion % scope
  )

  def apply() = compile ++ test
}
