package by.danilov.wow.guild.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper

class JacksonObjectMapper {
    companion object {
        val instance: ObjectMapper = ObjectMapper();
        init {
            instance.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
            instance.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }
    }
}
