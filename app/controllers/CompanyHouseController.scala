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

import play.api.Logging
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source

@Singleton
class CompanyHouseController @Inject()(cc: ControllerComponents) extends BackendController(cc) with Logging {

  implicit val ec: ExecutionContext = cc.executionContext
  private def getJsonResponse(path: String) = {
    Json.parse(Source.fromInputStream(getClass.getResourceAsStream(s"/resources/json/$path"), "utf-8").mkString)
  }

  def returnJson(crn: String): Action[AnyContent] = Action.async { implicit request =>
    logger.info(s"[CompanyHouseController][returnJson] Headers are ${request.headers}")

    if(request.headers.hasHeader("Authorization")){
      crn match{
        case "00000200"=> Future.successful(Ok(getJsonResponse("200-CompanyHouseResponse.json")))
        case "00000404"=> Future.successful(NotFound(getJsonResponse("404-CompanyHouseResponse.json")))
        case "00000429"=> Future.successful(TooManyRequests(getJsonResponse("429-CompanyHouseResponse.json")))
        case _ => Future.successful(Ok(getJsonResponse("200-CompanyHouseResponse.json")))
      }

    } else {
      logger.warn("[CompanyHouseController][returnJson] Missing Authorization header")
      Future.successful(BadRequest("Authorization Header is missing"))
    }
  }
}
