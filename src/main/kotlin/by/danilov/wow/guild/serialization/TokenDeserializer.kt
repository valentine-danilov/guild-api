package by.danilov.wow.guild.serialization

import by.danilov.wow.guild.domain.auth.Token
import by.danilov.wow.guild.serialization.api.ResponseDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.core.ResponseDeserializable
import org.springframework.stereotype.Component

@Component
class TokenDeserializer(private var objectMapper: ObjectMapper) : ResponseDeserializable<Token>, ResponseDeserializer<Token> {
    override fun deserialize(content: String): Token? {
        return objectMapper.readValue(content, Token::class.java)
    }
}
