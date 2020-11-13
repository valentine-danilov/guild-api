package by.danilov.wow.guild.serialization

import by.danilov.wow.guild.domain.auth.Token
import com.github.kittinunf.fuel.core.ResponseDeserializable
import org.springframework.stereotype.Component

@Component
class TokenDeserializer : ResponseDeserializable<Token> {
    override fun deserialize(content: String): Token? {
        return JacksonObjectMapper.instance.readValue(content, Token::class.java)
    }
}
