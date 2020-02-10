package controllers

import javax.inject.{Inject, Singleton}
import play.api.Logger
import java.io.File
import play.api.Environment
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.play.bootstrap.controller.BackendController

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PdfServiceController @Inject()(cc: ControllerComponents) extends BackendController(cc) {

  implicit val ec: ExecutionContext = cc.executionContext

  def generate: Action[AnyContent] = Action.async { implicit request =>

    val html = request.body.toString

    if(html.contains("html")) {
      Logger.info(s"[PdfServiceController][generating pdf]")
      Future.successful(Ok.sendFile(
        new File("conf/resources/sample.pdf"),
        inline = false
      ).as("application/pdf"))
    } else {
      Logger.warn(s"[PdfServiceController][unable to send pdf]")
      Future.successful(BadRequest("400 - Bad Request (stub)"))
    }
  }
}
