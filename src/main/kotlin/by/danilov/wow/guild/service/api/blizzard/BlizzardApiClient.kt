package by.danilov.wow.guild.service.api.blizzard

import by.danilov.wow.guild.serialization.api.ResponseDeserializer
import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.core.ResponseDeserializable

interface BlizzardApiClient {
    fun <T : Any> authorizedGetRequest(
        url: String,
        deserializer: ResponseDeserializer<T>,
        queryParams: List<Pair<String, Any?>> = listOf(),
    ): T
}
