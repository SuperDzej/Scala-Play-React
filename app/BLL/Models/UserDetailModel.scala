package BLL.Models

case class UserDetailModel (id: Option[Long], description: String, country: String, religion:String,
                       height: Double, weight: Double, skin: String, hair: String, gender: String,
                            age: Short, userId: Option[Long])
