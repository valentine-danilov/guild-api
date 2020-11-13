package by.danilov.wow.guild.controller

import by.danilov.wow.guild.service.http.client.BlizzardApiHttpClient
import io.javalin.http.Context
import org.springframework.stereotype.Service

@Service
class GuildController (var blizzardApiHttpClient: BlizzardApiHttpClient) {

    fun getGuildRoster(context: Context) {
        val realmSlug = context.pathParam("realmSlug")
        val nameSlug = context.pathParam("nameSlug")
        val region = context.queryParam<String>("region").get()
        val namespace = context.queryParam<String>("namespace").get()
        val locale = context.queryParam<String>("locale", "en_US").get()

        val guildRoster = blizzardApiHttpClient.getGuildRoster(region, namespace, realmSlug, nameSlug, locale);
        context.json(guildRoster)
    }

}