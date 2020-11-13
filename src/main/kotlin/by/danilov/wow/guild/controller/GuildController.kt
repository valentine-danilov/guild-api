package by.danilov.wow.guild.controller

import by.danilov.wow.guild.service.blizzard.BlizzardApiService
import io.javalin.http.Context
import org.springframework.stereotype.Service

@Service
class GuildController (var blizzardApiService: BlizzardApiService) {

    fun getGuildRoster(context: Context) {
        val realmSlug = context.pathParam("realmSlug")
        val nameSlug = context.pathParam("nameSlug")
        val region = context.queryParam<String>("region").get()
        val namespace = context.queryParam<String>("namespace").get()
        val locale = context.queryParam<String>("locale", "en_US")
            .check({it.matches(Regex("[a-z]{2}_[A-Z]{2}"))}, "Locale must match '[a-z]{2}_[A-Z]{2}' pattern")
            .get()

        val guildRoster = blizzardApiService.getGuildRoster(region, namespace, realmSlug, nameSlug, locale);
        context.json(guildRoster)
    }

}
