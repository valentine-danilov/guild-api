package by.danilov.wow.guild.service.blizzard

import by.danilov.wow.guild.domain.auth.Token
import by.danilov.wow.guild.serialization.TokenDeserializer
import by.danilov.wow.guild.serialization.api.ResponseDeserializer
import by.danilov.wow.guild.service.api.blizzard.BlizzardApiClient
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.core.extensions.authentication
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import kotlin.IllegalArgumentException

@Service
open class BlizzardApiFuelClient(private var tokenDeserializer: TokenDeserializer) : BlizzardApiClient {

    private var token: Token? = null

    @Value("\${blizzard.api.client-id}")
    private lateinit var clientId: String

    @Value("\${blizzard.api.client-secret}")
    private lateinit var clientSecret: String

    @Value("\${blizzard.api.auth.path}")
    private lateinit var authPath: String

    private fun getOauthToken(clientId: String, clientSecret: String): String {

        if (clientId.isEmpty() || clientSecret.isEmpty()) {
            throw IllegalArgumentException("Client ID and Client Secret must not be null")
        }

        if (token == null || token!!.isExpired()) {
            token = Fuel.post(authPath, listOf("grant_type" to "client_credentials"))
                .authentication()
                .basic(clientId, clientSecret)
                .responseObject(tokenDeserializer).third.get()
        }
        return token!!.accessToken
    }

    @Suppress("UNCHECKED_CAST")
    @Cacheable("cache_blizzardApi", keyGenerator = "apiRequestKeyGenerator")
    override fun <T : Any> authorizedGetRequest(
        url: String,
        deserializer: ResponseDeserializer<T>,
        queryParams: Parameters,
    ): T {
        if (deserializer !is ResponseDeserializable<*>) {
            throw IllegalArgumentException("Deserializer should be of type com.github.kittinunf.fuel.core.ResponseDeserializable<T> when using Fuel implementation")
        }

        return Fuel.get(url, queryParams)
            .authentication()
            .bearer(getOauthToken(clientId, clientSecret))
            .responseObject(deserializer as ResponseDeserializable<T>).third.get()
    }
}
