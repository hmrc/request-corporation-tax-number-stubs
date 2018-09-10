package controllers

import play.api.Play.current
import play.api.mvc.Action
import play.api.{Logger, Play}
import uk.gov.hmrc.play.microservice.controller.BaseController
import uk.gov.hmrc.stubs.StubResource

import scala.concurrent.Future

class PdfServiceController extends BaseController with StubResource {

  def generate = Action.async { implicit request =>

    val html = request.body.toString

    if(html.contains("html")) {
      Logger.info(s"[PdfServiceController][generating pdf]")
      Future.successful(Ok.sendFile(Play.application.getFile("conf/resources/sample.pdf")))
    } else {
      Logger.info(s"[PdfServiceController][unable to send pdf]")
      Future.successful(BadRequest("400 - Bad Request (stub)"))
    }
  }
}
