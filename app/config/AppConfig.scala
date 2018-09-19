package config

import javax.inject.Inject
import play.api.Mode.Mode
import play.api.{Configuration, Environment}
import uk.gov.hmrc.play.config.ServicesConfig

class AppConfig @Inject()(override val runModeConfiguration: Configuration, environment: Environment) extends ServicesConfig {
  override protected def mode: Mode = environment.mode
  lazy val callbackUrl: String = baseUrl("request-corporation-tax-number")
}
