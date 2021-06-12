package by.danilov.wow.guild.client

import by.danilov.wow.guild.serialization.MutableMapDeserializer
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.core.extensions.authentication
import io.micrometer.core.annotation.Timed
import org.springframework.stereotype.Component

@Component
open class BlizzardApiFuelClient(private var blizzardAuthClient: BlizzardAuthClient, private var responseDeserializer: MutableMapDeserializer) {

    @Timed("blizzard.api.fuel.client")
    open fun get(requestUrl: String, parameters: Parameters): MutableMap<String, Any> {
        return Fuel.get(requestUrl, parameters)
            .authentication()
            .bearer(blizzardAuthClient.getOauthToken().accessToken)
            .responseObject(responseDeserializer)
            .third.get()
    }

}