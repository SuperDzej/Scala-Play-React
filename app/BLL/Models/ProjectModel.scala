package BLL.Models

import java.sql.Timestamp
import java.text.SimpleDateFormat

import play.api.libs.json._

case class ProjectModel (id: Option[Long], name: String, description: String, url: String,
                         startDate: Timestamp, endDate: Option[Timestamp], skills: Seq[SkillModel])

object ProjectModel {
  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    def reads(json: JsValue): JsSuccess[Timestamp] = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }
    def writes(ts: Timestamp) = JsString(format.format(ts))
  }

  implicit val projectWrites: Writes[ProjectModel] = Json.writes[ProjectModel]
  implicit val projectReads: Reads[ProjectModel] = Json.reads[ProjectModel]
}
