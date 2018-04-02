package BLL.Models

case class UserModel(id: Long, firstName: String, lastName: String, mobile: Long, email: String, password: Option[String])
