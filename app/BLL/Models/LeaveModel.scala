package BLL.Models

import java.sql.Timestamp
import java.text.SimpleDateFormat

import play.api.libs.json._

case class LeaveModel(id: Option[Long], description: String, var category: String, startDate: Timestamp, endDate: Timestamp)

object LeaveModel {
  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    def reads(json: JsValue): JsSuccess[Timestamp] = {
      val str = json.as[String]
      val parsedDate = new Timestamp(format.parse(str).getTime)
      JsSuccess(parsedDate)
    }

    def writes(ts: Timestamp) = JsString(format.format(ts))
  }
  implicit val vacationsRead: Reads[LeaveModel] = Json.reads[LeaveModel]
  implicit val vacationsWrite: Writes[LeaveModel] = Json.writes[LeaveModel]
}