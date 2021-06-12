package by.danilov.wow.guild.serialization

import by.danilov.wow.guild.serialization.api.ResponseDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.core.ResponseDeserializable
import org.springframework.stereotype.Component

@Component
class MutableMapDeserializer(private var objectMapper: ObjectMapper) : ResponseDeserializable<MutableMap<String, Any>>,
    ResponseDeserializer<MutableMap<String, Any>> {

    override fun deserialize(content: String): MutableMap<String, Any>? {
        val typeReference = MutableMapStringObjectTypeReference()
        return objectMapper.readValue(content, typeReference)
    }
}
