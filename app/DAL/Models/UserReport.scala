package DAL.Models

import java.sql.Timestamp

case class UserReport (id: Long, reason: String, description: String, date: Timestamp,
                       userId: Long, reportedUserId: Long)
