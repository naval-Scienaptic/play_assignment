package models

import play.api.libs.json._

case class SaveRequest(start: Int, end: Int)

object SaveRequest {
  implicit val format = Json.format[SaveRequest]
}
