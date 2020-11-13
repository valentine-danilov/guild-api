package by.danilov.wow.guild.service.http.client

import by.danilov.wow.guild.domain.auth.Token
import by.danilov.wow.guild.domain.guild.GuildRoster
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.authentication
import org.springframework.stereotype.Service

@Service
class BlizzardApiHttpClient {

    private var token: Token? = null

    private fun getOauthToken(clientId: String, clientSecret: String) : String {
        if (token == null || token!!.isExpired()) {
            token = Fuel.post("https://us.battle.net/oauth/token", listOf("grant_type" to "client_credentials"))
                .authentication()
                .basic(clientId, clientSecret)
                .responseObject(Token.Deserializer()).third.get()
        }
        return token!!.accessToken
    }

    fun getGuildRoster(region: String, namespace: String, realmSlug: String, nameSlug: String, locale: String = "en_US") : GuildRoster {
        return Fuel.get("https://eu.api.blizzard.com/data/wow/guild/$realmSlug/$nameSlug/roster", listOf(
            ":region" to region,
            "namespace" to namespace,
            "locale" to locale))
            .authentication()
            .bearer(getOauthToken("d41d7ab3baaf463f83b2d1f660ceecb5", "DIF93Pc7ZlG6z3r3p0lCZDbVGbDe9NU2"))
            .responseObject(GuildRoster.Deserializer()).third.get();
    }

}