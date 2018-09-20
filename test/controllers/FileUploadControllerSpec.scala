package controllers

import config.AppConfig
import org.mockito.Matchers.any
import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{JsArray, JsValue, Json}
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}
import play.api.test.FakeRequest
import uk.gov.hmrc.play.test.WithFakeApplication

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


class FileUploadControllerSpec extends PlaySpec with WithFakeApplication with MockitoSugar {

  "File-upload controller" should {

    "create an envelope" in {
      val sut = createSUT
      val result = Await.result(sut.createEnvelope()(FakeRequest("POST", "")), 5.seconds)

      result.header.headers("Location").split("/").reverse.head mustBe sut.envelopeId
    }

    "upload a file" in {
      val sut = createSUT
      val mockResponse = createMockResponse(200, "")
      val mockRequest = mock[WSRequest]


      when(mockRequest.post(any[JsValue]())(any())).thenReturn(Future.successful(mockResponse))
      when(sut.wSClient.url(any())).thenReturn(mockRequest)

      val result = Await.result(sut.upLoadFile("envID","fileID")(FakeRequest("POST", "")), 5.seconds)

      result.header.status mustBe 200
    }

    "provide envelope summary" in {
      val sut = createSUT
      val body = Json.obj(
        "id" -> sut.envelopeId,
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

      val mockResponse = createMockResponse(200, body.toString())
      val mockRequest = mock[WSRequest]


      when(mockRequest.get()).thenReturn(Future.successful(mockResponse))
      when(sut.wSClient.url(any())).thenReturn(mockRequest)

      val result = Await.result(sut.envelopeSummary("envID")(FakeRequest("GET", "")), 5.seconds)

      result.header.status mustBe 200
    }

    "close an envelope" in {
      val sut = createSUT
      val result = Await.result(sut.closeEnvelope()(FakeRequest("POST", "")), 5.seconds)

      result.header.headers("Location").split("/").reverse.head mustBe sut.envelopeId
    }
  }

  private def createMockResponse(status: Int, body: String): WSResponse = {
    val wsResponse = mock[WSResponse]
    when(wsResponse.status).thenReturn(status)
    when(wsResponse.body).thenReturn(body)
    wsResponse
  }

  implicit val appConfig: AppConfig = mock[AppConfig]
  val mockWSClient: WSClient = mock[WSClient]

  def createSUT = new SUT
  class SUT extends FileUploadController(mockWSClient, appConfig) {

  }
}
