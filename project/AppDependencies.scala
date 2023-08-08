import play.core.PlayVersion
import play.sbt.PlayImport.ws
import sbt.{ModuleID, _}

object AppDependencies {

  private lazy val scope: String = "test"
  private val bootstrapVersion = "7.14.0"

  private val compile = Seq(
    ws,
    "uk.gov.hmrc" %% "bootstrap-backend-play-28" % bootstrapVersion,
    "uk.gov.hmrc" %% "domain" % "8.3.0-play-28"
  )

  private val test: Seq[ModuleID] = Seq(

    "org.pegdown"            %  "pegdown"                 % "1.6.0" % scope,
    "com.vladsch.flexmark"   %  "flexmark-all"            % "0.64.8" % scope,
    "com.typesafe.play"      %% "play-test"               % PlayVersion.current % scope,
    "org.scalatestplus"      %% "mockito-3-4"             % "3.2.10.0" % scope,
    "org.scalatestplus.play" %% "scalatestplus-play"      % "5.1.0" % scope,
    "uk.gov.hmrc"            %% "bootstrap-test-play-28"  % bootstrapVersion % scope
  )

  def apply(): Seq[ModuleID] = compile ++ test

}
