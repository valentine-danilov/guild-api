package by.danilov.wow.guild.service.api.blizzard

import by.danilov.wow.guild.domain.request.BlizzardApiRequestEntity

interface BlizzardApiService {

    fun getGuildRoster(requestEntity: BlizzardApiRequestEntity): MutableMap<String, Any>

    fun getCharacterProfileSummary(requestEntity: BlizzardApiRequestEntity): MutableMap<String, Any>

}
