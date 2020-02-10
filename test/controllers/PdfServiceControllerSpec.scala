package controllers

import helpers.TestFixture
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, contentType, _}

class PdfServiceControllerSpec extends TestFixture {

  lazy val pdfServiceController = new PdfServiceController(stubCC)

  "PdfServiceController" should {

    "return a pdf when html is passed in" in {

      val request = FakeRequest("POST","").withFormUrlEncodedBody("html" -> "<html")

      val response = pdfServiceController.generate().apply(request)

      status(response) mustBe OK
      contentType(response) mustBe Some("application/pdf")
    }

    "return a Bad Request when something other than html is passed in" in {

      val request = FakeRequest("POST", "").withFormUrlEncodedBody("test" -> "test")

      val response = pdfServiceController.generate().apply(request)

      status(response) mustBe BAD_REQUEST
      contentAsString(response) mustBe "400 - Bad Request (stub)"
    }
  }
}
