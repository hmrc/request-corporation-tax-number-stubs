
package config

import com.google.inject.{ImplementedBy, Singleton}
import uk.gov.hmrc.play.config.ServicesConfig

@ImplementedBy(classOf[AppConfigImpl])
trait AppConfig extends ServicesConfig {

  lazy val callbackUrl = baseUrl("request-corporation-tax-number")

}

@Singleton
class AppConfigImpl extends AppConfig
