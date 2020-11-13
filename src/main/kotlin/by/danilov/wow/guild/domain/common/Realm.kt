package by.danilov.wow.guild.domain.common

import com.fasterxml.jackson.annotation.JsonProperty

data class Realm(
	@JsonProperty("key") val key: Key,
	@JsonProperty("id") val id: Int,
	@JsonProperty("slug") val slug: String,
	@JsonProperty("name") val name: String?
)