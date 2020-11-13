package by.danilov.wow.guild.service.api.blizzard

import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.core.ResponseDeserializable

interface BlizzardApiClient {
    fun <T : Any> authorizedGetRequest(
        url: String,
        deserializer: ResponseDeserializable<T>,
        queryParams: Parameters = listOf(),
    ): T
}
