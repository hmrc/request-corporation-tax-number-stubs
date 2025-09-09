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

package models

import play.api.libs.json._
import java.time.LocalDate
import play.api.libs.json.JsonNaming.SnakeCase


case class CompanyDetails(companyName: String, dateOfCreation: Option[LocalDate])

object CompanyDetails {
  implicit val config: JsonConfiguration = JsonConfiguration(SnakeCase)
  implicit val localDateFormat: Format[LocalDate] = Format(Reads.DefaultLocalDateReads, Writes.DefaultLocalDateWrites)
  implicit val format: OFormat[CompanyDetails] = Json.format[CompanyDetails]
}
