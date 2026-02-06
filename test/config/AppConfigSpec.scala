/*
 * Copyright 2026 HM Revenue & Customs
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

package config

import org.mockito.Mockito._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

class AppConfigSpec extends AnyWordSpec with Matchers {

  "AppConfig" should {

    "return the callbackUrl from ServicesConfig" in {
      val mockServicesConfig = mock(classOf[ServicesConfig])

      when(mockServicesConfig.baseUrl("request-corporation-tax-number"))
        .thenReturn("http://example.com/ct")

      val appConfig = new AppConfig(mockServicesConfig)

      appConfig.callbackUrl shouldBe "http://example.com/ct"
    }
  }
}
