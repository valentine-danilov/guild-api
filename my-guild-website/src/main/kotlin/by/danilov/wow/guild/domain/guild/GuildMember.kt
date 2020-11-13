package by.danilov.wow.guild.domain.guild

import by.danilov.wow.guild.domain.character.Character
import com.fasterxml.jackson.annotation.JsonProperty

data class GuildMember(
	@JsonProperty("character") val character: Character,
	@JsonProperty("rank") val rank: Int
)