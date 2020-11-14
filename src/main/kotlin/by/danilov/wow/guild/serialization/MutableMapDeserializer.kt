package by.danilov.wow.guild.serialization

import by.danilov.wow.guild.serialization.api.ResponseDeserializer
import com.github.kittinunf.fuel.core.ResponseDeserializable
import org.springframework.stereotype.Component

@Component
class MutableMapDeserializer : ResponseDeserializable<MutableMap<String, Any>>,
    ResponseDeserializer<MutableMap<String, Any>> {

    override fun deserialize(content: String): MutableMap<String, Any>? {
        val typeReference = MutableMapStringObjectTypeReference()
        return JacksonObjectMapper.instance.readValue(content, typeReference)
    }
}
