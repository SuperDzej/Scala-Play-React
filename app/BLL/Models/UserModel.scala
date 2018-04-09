package BLL.Models

case class UserModel(id: Option[Long], firstName: String, lastName: String,
                     email: String, username: String, password: Option[String],
                     details: Option[UserDetailModel])
