package BLL.Models

case class UserModel(id: Long, firstName: String, lastName: String, mobile: String,
                     email: String, username: String, password: Option[String])
