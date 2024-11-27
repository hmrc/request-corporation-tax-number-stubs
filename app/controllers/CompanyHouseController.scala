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

import models.CompanyDetails
import play.api.Logging
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import java.time.{LocalDate, ZoneId}
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source

@Singleton
class CompanyHouseController @Inject()(cc: ControllerComponents) extends BackendController(cc) with Logging {

  implicit val ec: ExecutionContext = cc.executionContext

  private def getJsonResponse(path: String): JsValue = {
    Json.parse(Source.fromInputStream(getClass.getResourceAsStream(s"/resources/json/$path"), "utf-8").mkString)
  }

  private def createJsonResponseWithDateOfCreation(daysSinceCompanyCreation: Int): JsValue = {
    val companyDetailsFromFile = getJsonResponse("200-CompanyHouseResponse.json").as[CompanyDetails]

    val companyCreationDate = LocalDate.now(ZoneId.of("GMT")).minusDays(daysSinceCompanyCreation)
    val companyDetails = companyDetailsFromFile.copy(dateOfCreation = Some(companyCreationDate))

    Json.toJson(companyDetails)
  }

  def returnJson(companyReferenceNumber: String): Action[AnyContent] = Action.async { implicit request =>
    logger.info(s"[CompanyHouseController][returnJson] Headers are ${request.headers}")

    if (request.headers.hasHeader("Authorization")) {
      Future.successful {
        companyReferenceNumber match {
          case "00000200" => Ok(getJsonResponse("200-CompanyHouseResponse.json"))
          case "00000007" => Ok(createJsonResponseWithDateOfCreation(daysSinceCompanyCreation = 7))
          case "00000008" => Ok(createJsonResponseWithDateOfCreation(daysSinceCompanyCreation = 8))
          case "00000065" => Ok(createJsonResponseWithDateOfCreation(daysSinceCompanyCreation = 65))
          case "00000404" => NotFound(getJsonResponse("404-CompanyHouseResponse.json"))
          case "00000429" => TooManyRequests(getJsonResponse("429-CompanyHouseResponse.json"))
          case _ => Ok(getJsonResponse("200-CompanyHouseResponse.json"))
        }
      }
    } else {
      logger.warn("[CompanyHouseController][returnJson] Missing Authorization header")
      Future.successful(BadRequest("Authorization Header is missing"))
    }
  }
}
