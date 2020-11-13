package by.danilov.wow.guild.domain.guild

import by.danilov.wow.guild.domain.common.Faction
import by.danilov.wow.guild.domain.common.Key
import by.danilov.wow.guild.domain.common.Realm
import com.fasterxml.jackson.annotation.JsonProperty

data class Guild (
	@JsonProperty("key") val key : Key,
	@JsonProperty("name") val name : String,
	@JsonProperty("id") val id : Int,
	@JsonProperty("realm") val realm : Realm,
	@JsonProperty("faction") val faction : Faction
)