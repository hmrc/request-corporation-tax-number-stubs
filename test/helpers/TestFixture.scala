package helpers

import akka.stream.Materializer
import config.AppConfig
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.inject.Injector
import play.api.libs.ws.WSClient
import play.api.mvc._
import play.api.test.Helpers.stubControllerComponents

import scala.concurrent.ExecutionContext
import scala.reflect.ClassTag

trait TestFixture extends PlaySpec with MockitoSugar with GuiceOneAppPerSuite {
  val app: Application
  def real[T: ClassTag]: T = injector.instanceOf[T]

  implicit lazy val materializer: Materializer = app.injector.instanceOf[Materializer]
  lazy val injector: Injector = app.injector
  implicit lazy val ec: ExecutionContext = injector.instanceOf[ExecutionContext]

  val config: AppConfig = mock[AppConfig]
  val stubCC: ControllerComponents = stubControllerComponents()
  val mockWSClient: WSClient = mock[WSClient]
}
