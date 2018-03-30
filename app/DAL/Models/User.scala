package DAL.Models

import play.api.libs.json.Json

case class User(id: Long, firstName: String, lastName: String, mobile: Long, email: String)
