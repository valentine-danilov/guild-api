package by.danilov.wow.guild.domain.common

import com.fasterxml.jackson.annotation.JsonProperty

data class Links (
	@JsonProperty("self") val self : Key
)