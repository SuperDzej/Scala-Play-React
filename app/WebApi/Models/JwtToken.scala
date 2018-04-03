package WebApi.Models

case class JwtToken(token: String, schema: String, expires_in: Option[Int])
