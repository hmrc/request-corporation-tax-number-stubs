package controllers

import config.AppConfig
import javax.inject.{Inject, Singleton}
import play.api.Logger
import play.api.libs.json.{JsArray, Json}
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.play.bootstrap.controller.BackendController

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FileUploadController @Inject()(val wSClient: WSClient,
                                     config: AppConfig,
                                     cc: ControllerComponents) extends BackendController(cc) {

  implicit val ec: ExecutionContext = cc.executionContext

  val envelopeId = "123-234-345-456"

  def createEnvelope(): Action[AnyContent] = Action.async {
    Logger.info(s"[Fileupload][Create Envelope] ID = $envelopeId")
    Future.successful(Created.withHeaders("Location" -> s"http://stubs/$envelopeId"))
  }

  def closeEnvelope(): Action[AnyContent] = Action.async {
    Logger.info(s"[Fileupload][Close Envelope] ID = $envelopeId")
    Future.successful(Created.withHeaders("Location" -> s"http://stubs/$envelopeId"))
  }

  def envelopeSummary(envId:String): Action[AnyContent] = Action.async {
    Logger.info(s"[Fileupload][Envelope Summary] ID = $envId")
    Future.successful(
      Ok(
        Json.obj(
          "id" -> envId,
          "status" -> "OPEN",
          "files" -> JsArray(
            Seq(
              Json.obj(
                "name" -> "metadata",
                "status" -> "AVAILABLE"),
              Json.obj(
                "name" -> "iform",
                "status" -> "AVAILABLE"),
              Json.obj(
                "name" -> "robotic",
                "status" -> "AVAILABLE")
            )
          )
        )
      ).withHeaders("Location" -> s"http://stubs/$envelopeId")
    )
  }

  def upLoadFile(envId: String, fileId: String): Action[AnyContent] = Action.async {
    Future {
      val json =
        s"""{
           "envelopeId": "$envId",
           "fileId": "$fileId",
            "status": "AVAILABLE"
         }""".stripMargin

      val url = s"${config.callbackUrl}/request-corporation-tax-number/file-upload/callback"
      Logger.info(s"[Fileupload][Upload File] ID = $envelopeId")
      wSClient
        .url(url)
        .post(Json.parse(json))

      Ok
    }
  }
}
