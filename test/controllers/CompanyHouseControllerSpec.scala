/*
 * Copyright 2024 HM Revenue & Customs
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
import models.CompanyDetails
import org.scalatest.concurrent.ScalaFutures
import play.api.libs.json._
import play.api.mvc.AnyContentAsFormUrlEncoded
import play.api.test.FakeRequest
import play.api.test.Helpers._

import java.time.LocalDate

class CompanyHouseControllerSpec extends TestFixture with ScalaFutures {
  lazy val CompanyHouseController = new CompanyHouseController(stubCC)
  val request: FakeRequest[AnyContentAsFormUrlEncoded] = FakeRequest("GET", "")
    .withFormUrlEncodedBody("html" -> "<html")
    .withHeaders("Authorization" -> "")

  "CompanyHouseController" should {

    "return an OK response with company name, given a request for company 00000200" in {
      val response = CompanyHouseController.returnJson("00000200").apply(request)
      val expectedResult = """{"company_name":"company"}""".stripMargin

      contentAsString(response) mustEqual expectedResult
      status(response) mustBe OK
      contentType(response) mustBe Some("application/json")
    }

    "return an OK response, with the company name and date of creation as 7 days ago, given a request for company 00000007" in {
      val response = CompanyHouseController.returnJson("00000007").apply(request)
      val expectedResult = Json.toJson(CompanyDetails("company", Some(LocalDate.now().minusDays(7))))

      contentAsString(response) mustEqual expectedResult.toString()
      status(response) mustBe OK
      contentType(response) mustBe Some("application/json")
    }

    "return an OK response, with the company name and date of creation as 8 days ago, given a request for company 00000008" in {
      val response = CompanyHouseController.returnJson("00000008").apply(request)
      val expectedResult = Json.toJson(CompanyDetails("company", Some(LocalDate.now().minusDays(8))))

      contentAsString(response) mustEqual expectedResult.toString()
      status(response) mustBe OK
      contentType(response) mustBe Some("application/json")
    }

    "return a NOT_FOUND response, with relevant errors, given a request for company 00000404" in {
      val response = CompanyHouseController.returnJson("00000404").apply(request)

      val expectedError =
        """{"errors":[{"type":"ch:service","error":"company-profile-not-found"}]}""".stripMargin

      contentAsString(response) mustEqual expectedError
      status(response) mustBe NOT_FOUND
      contentType(response) mustBe Some("application/json")
    }

    "return a TOO_MANY_REQUESTS response, given a request for company 00000429" in {
      val response = CompanyHouseController.returnJson("00000429").apply(request)

      status(response) mustBe TOO_MANY_REQUESTS
      contentType(response) mustBe Some("application/json")
    }

    "return an OK response, given a company number not in the expected values" in {
      val response = CompanyHouseController.returnJson("11111169").apply(request)

      status(response) mustBe OK
      contentType(response) mustBe Some("application/json")
    }

    "return BAD_REQUEST, given a request with no auth header" in {
      val request: FakeRequest[AnyContentAsFormUrlEncoded] = FakeRequest("GET", "")
        .withFormUrlEncodedBody("html" -> "<html")

      val response = CompanyHouseController.returnJson("00000200").apply(request)
      status(response) mustBe BAD_REQUEST
    }
  }
}
