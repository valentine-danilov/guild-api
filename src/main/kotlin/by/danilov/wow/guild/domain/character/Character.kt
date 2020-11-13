package by.danilov.wow.guild.domain.character

import by.danilov.wow.guild.domain.common.Key
import by.danilov.wow.guild.domain.common.Realm
import com.fasterxml.jackson.annotation.JsonProperty

data class Character(
	@JsonProperty("key") val key: Key,
	@JsonProperty("name") val name: String,
	@JsonProperty("id") val id: Int,
	@JsonProperty("realm") val realm: Realm,
	@JsonProperty("level") val level: Int,
	@JsonProperty("playable_class") val playable_class: PlayableClass,
	@JsonProperty("playable_race") val playable_race: PlayableRace
)