package by.danilov.wow.guild.domain.character

import by.danilov.wow.guild.domain.common.Key
import com.fasterxml.jackson.annotation.JsonProperty

data class PlayableRace (
	@JsonProperty("key") val key : Key,
	@JsonProperty("id") val id : Int
)