package by.danilov.wow.guild.client

import by.danilov.wow.guild.domain.auth.Token
import by.danilov.wow.guild.properties.BlizzardApiConfigProperties
import feign.auth.BasicAuthRequestInterceptor
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "blizzard-auth-client", url = "\${blizzard.api.auth.host}", configuration = [BlizzardAuthClientConfiguration::class])
interface BlizzardAuthClient {

    @PostMapping("/oauth/token")
    fun getOauthToken(@RequestParam("grant_type") grantType: String = "client_credentials"): Token

}

@Configuration
open class BlizzardAuthClientConfiguration {

    @Bean
    open fun basicAuthRequestInterceptor(blizzardApiConfigProperties: BlizzardApiConfigProperties) =
        BasicAuthRequestInterceptor(blizzardApiConfigProperties.auth.clientId, blizzardApiConfigProperties.auth.clientSecret)

}