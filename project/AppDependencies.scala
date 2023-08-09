import play.sbt.PlayImport.ws
import sbt.{ModuleID, _}

object AppDependencies {

  private val bootstrapVersion = "7.21.0"

  private val compile = Seq(
    ws,
    "uk.gov.hmrc" %% "bootstrap-backend-play-28" % bootstrapVersion,
    "uk.gov.hmrc" %% "domain" % "8.3.0-play-28"
  )

  private val test: Seq[ModuleID] = Seq(

    "com.vladsch.flexmark"   %  "flexmark-all"            % "0.64.8",
    "org.scalatest"          %% "scalatest"               % "3.2.16",
    "org.scalatestplus"      %% "mockito-4-11"            % "3.2.16.0",
    "uk.gov.hmrc"            %% "bootstrap-test-play-28"  % bootstrapVersion
  ).map(_ % Test)

  def apply(): Seq[ModuleID] = compile ++ test

}
