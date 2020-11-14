package by.danilov.wow.guild.serialization

import by.danilov.wow.guild.domain.auth.Token
import by.danilov.wow.guild.serialization.api.ResponseDeserializer
import com.github.kittinunf.fuel.core.ResponseDeserializable
import org.springframework.stereotype.Component

@Component
class TokenDeserializer : ResponseDeserializable<Token>, ResponseDeserializer<Token> {
    override fun deserialize(content: String): Token? {
        return JacksonObjectMapper.instance.readValue(content, Token::class.java)
    }
}
