package BLL.Models

case class UserReportModel (id: Option[Long], reason: String, description: String, date: Option[String],
                       userId: Long, reportedUserId: Long)
