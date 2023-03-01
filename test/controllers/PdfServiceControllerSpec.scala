/*
 * Copyright 2023 HM Revenue & Customs
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

package controllers

import helpers.TestFixture
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, contentType, _}

class PdfServiceControllerSpec extends TestFixture {

  lazy val pdfServiceController = new PdfServiceController(stubCC)

  "PdfServiceController" should {

    "return a pdf when html is passed in" in {

      val request = FakeRequest("POST","").withFormUrlEncodedBody("html" -> "<html")

      val response = pdfServiceController.generate().apply(request)

      status(response) mustBe OK
      contentType(response) mustBe Some("application/pdf")
    }

    "return a Bad Request when something other than html is passed in" in {

      val request = FakeRequest("POST", "").withFormUrlEncodedBody("test" -> "test")

      val response = pdfServiceController.generate().apply(request)

      status(response) mustBe BAD_REQUEST
      contentAsString(response) mustBe "400 - Bad Request (stub)"
    }
  }
}
