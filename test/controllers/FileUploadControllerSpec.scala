/*
 * Copyright 2025 HM Revenue & Customs
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
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import play.api.http.Status.OK
import play.api.libs.json.{JsArray, JsValue, Json}
import play.api.libs.ws.{WSRequest, WSResponse}
import play.api.test.FakeRequest

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


class FileUploadControllerSpec extends TestFixture {

  lazy val fileUploaderController = new FileUploadController(mockWSClient, config, stubCC)

  "File-upload controller" should {

    "create an envelope" in {
      val result = Await.result(fileUploaderController.createEnvelope()(FakeRequest("POST", "")), 5.seconds)

      result.header.headers("Location").split("/").reverse.head mustBe fileUploaderController.envelopeId
    }

    "upload a file" in {
      val mockResponse = createMockResponse(OK, "")
      val mockRequest = mock[WSRequest]

      when(mockRequest.post(any[JsValue]())(any())).thenReturn(Future.successful(mockResponse))
      when(fileUploaderController.wSClient.url(any())).thenReturn(mockRequest)

      val result = Await.result(fileUploaderController.upLoadFile("envID","fileID")(FakeRequest("POST", "")), 5.seconds)

      result.header.status mustBe 200
    }

    "provide envelope summary" in {
      val body = Json.obj(
        "id" -> fileUploaderController.envelopeId,
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

      val mockResponse = createMockResponse(OK, body.toString())
      val mockRequest = mock[WSRequest]


      when(mockRequest.get()).thenReturn(Future.successful(mockResponse))
      when(fileUploaderController.wSClient.url(any())).thenReturn(mockRequest)

      val result = Await.result(fileUploaderController.envelopeSummary("envID")(FakeRequest("GET", "")), 5.seconds)

      result.header.status mustBe 200
    }

    "close an envelope" in {
      val result = Await.result(fileUploaderController.closeEnvelope()(FakeRequest("POST", "")), 5.seconds)

      result.header.headers("Location").split("/").reverse.head mustBe fileUploaderController.envelopeId
    }
  }

  private def createMockResponse(status: Int, body: String): WSResponse = {
    val wsResponse = mock[WSResponse]
    when(wsResponse.status).thenReturn(status)
    when(wsResponse.body).thenReturn(body)
    wsResponse
  }
}
