package BLL.Models

case class UserReportModel (id: Long, reason: String, description: String, date: String,
                       userId: Long, reportedUserId: Long)
