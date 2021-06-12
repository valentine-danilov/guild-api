package by.danilov.wow.guild.service.blizzard

import by.danilov.wow.guild.client.BlizzardApiClient
import by.danilov.wow.guild.domain.request.BlizzardApiRequestEntity
import by.danilov.wow.guild.service.api.blizzard.BlizzardApiService
import by.danilov.wow.guild.service.api.blizzard.processor.BlizzardApiProcessorService
import by.danilov.wow.guild.service.blizzard.processor.ProcessingType
import feign.FeignException
import org.springframework.stereotype.Service
import kotlin.jvm.Throws
import org.springframework.cache.annotation.Cacheable

@Service
open class BlizzardApiServiceImpl(
    private val blizzardApiClient: BlizzardApiClient,
    private val blizzardApiProcessorService: BlizzardApiProcessorService
) : BlizzardApiService {

    @Cacheable("cache_guildRoster")
    @Throws(FeignException::class)
    override fun getGuildRoster(
        requestEntity: BlizzardApiRequestEntity
    ): MutableMap<String, Any> {
        val guildRoster = blizzardApiClient.getGuildRoster(
            requestEntity.nameSlug,
            requestEntity.realmSlug,
            requestEntity.region,
            requestEntity.namespace,
            requestEntity.locale
        )
        blizzardApiProcessorService.process(guildRoster, requestEntity.locale, ProcessingType.GUILD_ROSTER)
        return guildRoster
    }

    @Cacheable("cache_characterProfileSummary")
    @Throws(FeignException::class)
    override fun getCharacterProfileSummary(
        requestEntity: BlizzardApiRequestEntity
    ): MutableMap<String, Any> {
        val characterProfileSummary = blizzardApiClient.getCharacterProfileSummary(
            requestEntity.characterName,
            requestEntity.realmSlug,
            requestEntity.region,
            requestEntity.namespace,
            requestEntity.locale
        )
        blizzardApiProcessorService.process(characterProfileSummary, requestEntity.locale, ProcessingType.CHARACTER)
        return characterProfileSummary
    }
}
