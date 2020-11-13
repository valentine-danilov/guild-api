package by.danilov.wow.guild.domain.auth

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.core.ResponseDeserializable

data class Token(
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("expires_in") val expiresIn: Long,
    @JsonProperty("token_type") val tokenType: String
) {
    var expiresAt: Long = System.currentTimeMillis() + expiresIn

    fun isExpired(): Boolean {
        return System.currentTimeMillis() > expiresAt
    }

    class Deserializer : ResponseDeserializable<Token> {
        override fun deserialize(content: String): Token? {
            val objectMapper = ObjectMapper()
            return objectMapper.readValue(content, Token::class.java)
        }
    }
}