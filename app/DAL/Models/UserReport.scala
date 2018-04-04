package DAL.Models

import java.util.Calendar

case class UserReport (id: Long, reason: String, description: String, date: String,
                       userId: Long, reportedUserId: Long)
