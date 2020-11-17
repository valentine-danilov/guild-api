package by.danilov.wow.guild.controller

import by.danilov.wow.guild.service.api.blizzard.BlizzardApiService
import by.danilov.wow.guild.util.RequestEntityProvider
import io.javalin.http.Context
import org.springframework.stereotype.Service

@Service
class CharacterProfileController(var blizzardApiService: BlizzardApiService) {

    fun getCharacterProfileSummary(context: Context) {

        val requestEntity = RequestEntityProvider.contextToRequestEntity(context).apply {
            this.realmSlug = context.pathParam("realmSlug")
            this.nameSlug = context.pathParam("characterName")
        }

        val characterProfileSummary = blizzardApiService.getCharacterProfileSummary(requestEntity)
        context.json(characterProfileSummary)
    }

}
