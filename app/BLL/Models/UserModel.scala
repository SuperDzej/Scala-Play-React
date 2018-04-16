package BLL.Models

import play.api.libs.json.{Json, Reads, Writes}

case class UserModel(id: Option[Long], firstName: String, lastName: String,
                     email: String, username: String, password: Option[String],
                     details: Option[UserDetailModel])

object UserModel {
  implicit val userModelWrites: Writes[UserModel] = Json.writes[UserModel]
  implicit val userModelReads: Reads[UserModel] = Json.reads[UserModel]
}