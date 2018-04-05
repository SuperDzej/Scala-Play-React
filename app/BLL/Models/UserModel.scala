package BLL.Models

case class UserModel(id: Long, firstName: String, lastName: String,
                     email: String, username: String, password: Option[String])
