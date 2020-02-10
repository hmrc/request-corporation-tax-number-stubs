package config

import javax.inject.{Inject, Singleton}
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

@Singleton
class AppConfig @Inject()(servicesConfig: ServicesConfig) {
  lazy val callbackUrl: String = servicesConfig.baseUrl("request-corporation-tax-number")
}
