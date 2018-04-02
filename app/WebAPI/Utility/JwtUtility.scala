package WebApi.utilities

import authentikat.jwt.{JsonWebToken, JwtClaimsSet, JwtHeader}

import play.api.Configuration

import javax.inject._

class JwtUtility @Inject() (config: Configuration){
  val jwtSecretKey = config.get[String]("jwt.secretKey")
  val jwtSecretAlgo = config.get[String]("jwt.algorithm")

  def createToken(payload: String): String = {
    val header = JwtHeader(jwtSecretAlgo)
    val claimsSet = JwtClaimsSet(payload)

    JsonWebToken(header, claimsSet, jwtSecretKey)
  }

  def isValidToken(jwtToken: String): Boolean =
    JsonWebToken.validate(jwtToken, jwtSecretKey)

  def decodePayload(jwtToken: String): Option[String] =
    jwtToken match {
      case JsonWebToken(header, claimsSet, signature) => Option(claimsSet.asJsonString)
      case _ => None
    }
}
