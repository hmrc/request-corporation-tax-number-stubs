package controllers

import javax.inject.Inject
import config.AppConfig
import play.api.Logger
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, AnyContent}
import uk.gov.hmrc.play.microservice.controller.BaseController
import uk.gov.hmrc.stubs.StubResource

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class FileUploadController @Inject()(val wSClient: WSClient, config: AppConfig) extends BaseController with StubResource {

  val envelopeId = "123-234-345-456"

  def createEnvelope(): Action[AnyContent] = Action.async {
    Logger.info (s"[Fileupload][Create Envelope] ID = $envelopeId")
    Future.successful(Created.withHeaders("Location" -> s"http://stubs/$envelopeId"))
  }

  def closeEnvelope(): Action[AnyContent] = Action.async {
    Logger.info (s"[Fileupload][Close Envelope] ID = $envelopeId")
    Future.successful(Created.withHeaders("Location" -> s"http://stubs/$envelopeId"))
  }

  def upLoadFile(envId: String, fileId: String): Action[AnyContent] = Action.async {
    Future {
      val json =
        s"""{
           "envelopeId": "$envId",
           "fileId": "$fileId",
            "status": "AVAILABLE"
         }""".stripMargin

      val url = s"${config.callbackUrl}request-corporation-tax-number/file-upload/callback"
      Logger.info (s"[Fileupload][Upload File] ID = $envelopeId")
      wSClient
        .url(url)
        .post(Json.parse(json))

      Ok
    }
  }
}
