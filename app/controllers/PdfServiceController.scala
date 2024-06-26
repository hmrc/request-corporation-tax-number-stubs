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

import javax.inject.{Inject, Singleton}
import play.api.Logging
import java.io.File
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PdfServiceController @Inject()(cc: ControllerComponents) extends BackendController(cc) with Logging {

  implicit val ec: ExecutionContext = cc.executionContext

  def generate: Action[AnyContent] = Action.async { implicit request =>

    val html = request.body.toString

    if(html.contains("html")) {
      logger.info(s"[PdfServiceController][generating pdf]")
      Future.successful(Ok.sendFile(
        new File("conf/resources/sample.pdf"),
        inline = false
      ).as("application/pdf"))
    } else {
      logger.warn(s"[PdfServiceController][unable to send pdf]")
      Future.successful(BadRequest("400 - Bad Request (stub)"))
    }
  }
}
