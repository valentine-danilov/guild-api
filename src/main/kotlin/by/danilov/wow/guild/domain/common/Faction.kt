package by.danilov.wow.guild.domain.common

import com.fasterxml.jackson.annotation.JsonProperty

data class Faction (
	@JsonProperty("type") val type : String,
	@JsonProperty("name") val name : String
)