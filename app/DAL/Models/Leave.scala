package DAL.Models

import java.sql.Timestamp

case class Leave(id: Long, description: String, categoryId: Long, startDate: Timestamp, endDate: Timestamp)
