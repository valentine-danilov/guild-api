package by.danilov.wow.guild.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class SerializationConfiguration {

    @Bean
    open fun objectMapper() : ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return objectMapper
    }

}