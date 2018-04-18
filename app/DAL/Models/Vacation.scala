package DAL.Models

import java.sql.Timestamp

case class Vacation (id: Long, description: String, category: String, startDate: Timestamp, endDate: Timestamp)
