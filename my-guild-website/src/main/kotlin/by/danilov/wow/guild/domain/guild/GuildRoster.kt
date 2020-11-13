package by.danilov.wow.guild.domain.guild

import by.danilov.wow.guild.domain.common.Links
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.core.ResponseDeserializable

class GuildRoster (
    @JsonProperty("_links") val links : Links,
    @JsonProperty("guild") val guild : Guild,
    @JsonProperty("members") val members: List<GuildMember>
) {
    class Deserializer : ResponseDeserializable<GuildRoster> {
        override fun deserialize(content: String): GuildRoster? {
            val objectMapper = ObjectMapper()
            return objectMapper.readValue(content, GuildRoster::class.java)
        }
    }
}

