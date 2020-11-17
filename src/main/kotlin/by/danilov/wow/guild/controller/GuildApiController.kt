package by.danilov.wow.guild.controller

import by.danilov.wow.guild.domain.request.BlizzardApiRequestEntity
import by.danilov.wow.guild.service.api.blizzard.BlizzardApiService
import by.danilov.wow.guild.util.RequestEntityProvider
import io.javalin.http.Context
import org.springframework.stereotype.Service

@Service
class GuildApiController (var blizzardApiService: BlizzardApiService) {

    fun getGuildRoster(context: Context) {

        val requestEntity = RequestEntityProvider.contextToRequestEntity(context).apply {
            this.realmSlug = context.pathParam("realmSlug")
            this.nameSlug = context.pathParam("nameSlug")
        }

        val guildRoster = blizzardApiService.getGuildRoster(requestEntity)
        context.json(guildRoster)
    }

}
