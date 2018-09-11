package controllers

import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, contentType, _}
import uk.gov.hmrc.play.test.WithFakeApplication


class PdfServiceControllerSpec extends PlaySpec with WithFakeApplication with MockitoSugar{

  "PdfServiceController" should {

    "return a pdf when html is passed in" in {

      val sut = createSUT

      val request = FakeRequest("POST", "").withFormUrlEncodedBody("html" -> "<html")

      val response = sut.generate()(request)

      status(response) mustBe OK
      contentType(response) mustBe Some("application/pdf")
    }

    "return a Bad Request when something other than html is passed in" in {

      val sut = createSUT

      val request = FakeRequest("POST", "").withFormUrlEncodedBody("test" -> "test")

      val response = sut.generate()(request)

      status(response) mustBe BAD_REQUEST
      contentAsString(response) mustBe "400 - Bad Request (stub)"
    }
  }

  def createSUT = new SUT

  class SUT extends PdfServiceController()
}
