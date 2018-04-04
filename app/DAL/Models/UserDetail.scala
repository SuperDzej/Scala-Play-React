package DAL.Models

case class UserDetail (id: Long, description: String, country: String, religion:String,
                       height: Double, weight: Double, skin: String, hair: String, gender: String,
                       birthYear: Int, birthMonth: Int, birthDay: Int, userId: Long)
