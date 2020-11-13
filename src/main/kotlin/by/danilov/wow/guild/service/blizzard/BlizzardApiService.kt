package by.danilov.wow.guild.service.blizzard

import by.danilov.wow.guild.serialization.MutableMapDeserializer
import by.danilov.wow.guild.service.api.blizzard.BlizzardApiClient
import by.danilov.wow.guild.service.api.blizzard.processor.BlizzardApiProcessorService
import by.danilov.wow.guild.util.ParametersBuilder
import com.github.kittinunf.fuel.core.FuelError
import org.springframework.stereotype.Service
import kotlin.jvm.Throws
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable

@Service
open class BlizzardApiService(
    private val blizzardApiClient: BlizzardApiClient,
    private val deserializer: MutableMapDeserializer,
    private val blizzardApiProcessorService: BlizzardApiProcessorService
) {


    @Value("\${blizzard.api.host}")
    private lateinit var blizzardApiHost: String

    @Cacheable("cache_guildRoster")
    @Throws(FuelError::class)
    open fun getGuildRoster(
        region: String,
        namespace: String,
        realmSlug: String,
        nameSlug: String,
        locale: String = "en_US"
    ): MutableMap<String, Any> {
        val guildRoster = blizzardApiClient.authorizedGetRequest(
            "$blizzardApiHost/guild/$realmSlug/$nameSlug/roster",
            deserializer,
            ParametersBuilder.getBlizzardApiParameters(region, namespace, locale)
        )
        blizzardApiProcessorService.process(guildRoster, locale)
        return guildRoster
    }
}
