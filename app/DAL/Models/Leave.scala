package DAL.Models

import java.sql.Timestamp

case class Leave(id: Long, description: String, isApproved: Option[Boolean],
                 categoryId: Long, startDate: Timestamp, endDate: Timestamp)
