import play.sbt.PlayImport.ws
import sbt.{ModuleID, _}

object AppDependencies {

  private val bootstrapVersion = "9.11.0"

  private val compile = Seq(
    ws,
    "uk.gov.hmrc" %% "bootstrap-backend-play-30" % bootstrapVersion,
    "uk.gov.hmrc" %% "domain-play-30"            % "9.0.0"
  )

  private val test: Seq[ModuleID] = Seq(

    "com.vladsch.flexmark"   %  "flexmark-all"            % "0.64.8",
    "org.scalatest"          %% "scalatest"               % "3.2.19",
    "org.scalatestplus"      %% "mockito-5-10"            % "3.2.18.0",
    "uk.gov.hmrc"            %% "bootstrap-test-play-30"  % bootstrapVersion
  ).map(_ % Test)

  def apply(): Seq[ModuleID] = compile ++ test

}
