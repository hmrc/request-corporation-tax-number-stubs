/*
 * Copyright 2021 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package helpers

import akka.stream.Materializer
import config.AppConfig
import org.scalatestplus.mockito.MockitoSugar
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
