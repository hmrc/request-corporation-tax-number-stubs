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

package controllers

import helpers.TestFixture
import play.api.test.FakeRequest
import play.api.test.Helpers._

class CompanyHouseControllerSpec extends TestFixture {

  lazy val CompanyHouseController = new CompanyHouseController(stubCC)

  "CompanyHouseController" should {

    "return an ok json with the company name when a request with 00000200 is received " in {

      val request = FakeRequest("GET","").withFormUrlEncodedBody("html" -> "<html")

      val response = CompanyHouseController.returnJson("00000200").apply(request)

      status(response) mustBe OK
      contentType(response) mustBe Some("application/json")
    }


    "return a not found json with the company name when a request with 00000404 is received " in {

      val request = FakeRequest("GET","").withFormUrlEncodedBody("html" -> "<html")

      val response = CompanyHouseController.returnJson("00000404").apply(request)

      status(response) mustBe NOT_FOUND
      contentType(response) mustBe Some("application/json")
    }

    "return a not found json with the company name when a request with 00000429 is received " in {

      val request = FakeRequest("GET","").withFormUrlEncodedBody("html" -> "<html")

      val response = CompanyHouseController.returnJson("00000429").apply(request)

      status(response) mustBe TOO_MANY_REQUESTS
      contentType(response) mustBe Some("application/json")
    }

    "return an ok json with the company name when a request with anything else is received " in {

      val request = FakeRequest("GET","").withFormUrlEncodedBody("html" -> "<html")

      val response = CompanyHouseController.returnJson("11111169").apply(request)

      status(response) mustBe OK
      contentType(response) mustBe Some("application/json")
    }

  }
}
