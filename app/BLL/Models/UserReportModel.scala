package BLL.Models

import play.api.libs.json.{Json, Reads, Writes}

case class UserReportModel (id: Option[Long], reason: String, description: String, date: Option[String],
                       userId: Long, reportedUserId: Long)

object UserReportModel {
  implicit val reportReads: Reads[UserReportModel] = Json.reads[UserReportModel]
  implicit val reportWrites: Writes[UserReportModel] = Json.writes[UserReportModel]
}