package by.danilov.wow.guild.client

import by.danilov.wow.guild.domain.auth.Token
import feign.RequestInterceptor
import feign.RequestTemplate
import kotlinx.coroutines.*
import java.util.concurrent.Executors

open class BlizzardApiRequestInterceptor(private var blizzardAuthClient: BlizzardAuthClient): RequestInterceptor {

    private var token: Token? = null
    private var oauthTokenDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    override fun apply(template: RequestTemplate?) {
        if (token == null || token!!.isExpired()) {
            runBlocking {
                withContext(oauthTokenDispatcher) {
                    if (token == null || token!!.isExpired()) {
                        token = blizzardAuthClient.getOauthToken()
                    }
                }
            }
        }
        template?.header("Authorization", "Bearer ${token?.accessToken}")
    }
}