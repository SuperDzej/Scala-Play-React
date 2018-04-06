package DAL.Models

case class UserReport (id: Long, reason: String, description: String, date: String,
                       userId: Long, reportedUserId: Long)
