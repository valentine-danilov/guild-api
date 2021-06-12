package by.danilov.wow.guild.client

import by.danilov.wow.guild.serialization.MutableMapDeserializer
import by.danilov.wow.guild.serialization.api.ResponseDeserializer
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.authentication
import feign.Capability
import feign.RequestInterceptor
import feign.micrometer.MicrometerCapability
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "blizzard-api-client", url = "\${blizzard.api.host}", configuration = [BlizzardApiClientConfiguration::class])
interface BlizzardApiClient {

    @GetMapping("/data/wow/guild/{realmSlug}/{nameSlug}/roster")
    fun getGuildRoster(
        @PathVariable nameSlug: String,
        @PathVariable realmSlug: String,
        @RequestParam(":region") region: String,
        @RequestParam("namespace") namespace: String,
        @RequestParam("locale", required = false, defaultValue = "en_US") locale: String = "en_US"
    ): MutableMap<String, Any>

    @GetMapping("/profile/wow/character/{realmSlug}/{characterName}")
    fun getCharacterProfileSummary(
        @PathVariable characterName: String,
        @PathVariable realmSlug: String,
        @RequestParam(":region") region: String,
        @RequestParam("namespace") namespace: String,
        @RequestParam("locale", required = false, defaultValue = "en_US") locale: String = "en_US"
    ): MutableMap<String, Any>
}

@Configuration
open class BlizzardApiClientConfiguration {

    @Bean
    open fun requestInterceptor(blizzardAuthClient: BlizzardAuthClient): RequestInterceptor = BlizzardApiRequestInterceptor(blizzardAuthClient)

    @Bean
    open fun micrometerCapability(): Capability = MicrometerCapability()

}