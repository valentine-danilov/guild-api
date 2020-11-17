package by.danilov.wow.guild.service.blizzard

import by.danilov.wow.guild.domain.request.BlizzardApiRequestEntity
import by.danilov.wow.guild.serialization.MutableMapDeserializer
import by.danilov.wow.guild.service.api.blizzard.BlizzardApiClient
import by.danilov.wow.guild.service.api.blizzard.BlizzardApiService
import by.danilov.wow.guild.service.api.blizzard.processor.BlizzardApiProcessorService
import by.danilov.wow.guild.util.BlizzardApiPathBuilder
import com.github.kittinunf.fuel.core.FuelError
import org.springframework.stereotype.Service
import kotlin.jvm.Throws
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable

@Service
open class BlizzardApiServiceImpl(
    private val blizzardApiClient: BlizzardApiClient,
    private val deserializer: MutableMapDeserializer,
    private val blizzardApiProcessorService: BlizzardApiProcessorService
) : BlizzardApiService {

    @Value("\${blizzard.api.host}")
    private lateinit var blizzardApiHost: String

    @Cacheable("cache_guildRoster")
    @Throws(FuelError::class)
    override fun getGuildRoster(
        requestEntity: BlizzardApiRequestEntity
    ): MutableMap<String, Any> {
        val guildRoster = blizzardApiClient.authorizedGetRequest(
            BlizzardApiPathBuilder.builder(blizzardApiHost)
                .guildRoster(requestEntity.realmSlug, requestEntity.nameSlug),
            deserializer,
            requestEntity.queryParameters
        )
        blizzardApiProcessorService.process(guildRoster, requestEntity.locale)
        return guildRoster
    }

    @Cacheable("cache_characterProfileSummary")
    @Throws(FuelError::class)
    override fun getCharacterProfileSummary(
        requestEntity: BlizzardApiRequestEntity
    ): MutableMap<String, Any> {
        val characterProfileSummary = blizzardApiClient.authorizedGetRequest(
            BlizzardApiPathBuilder.builder(blizzardApiHost)
                .characterIndex(requestEntity.realmSlug, requestEntity.characterName),
            deserializer,
            requestEntity.queryParameters
        )
        blizzardApiProcessorService.process(characterProfileSummary, requestEntity.locale)
        return characterProfileSummary
    }
}
