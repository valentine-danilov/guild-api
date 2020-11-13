package by.danilov.wow.guild

import by.danilov.wow.guild.controller.GuildController
import by.danilov.wow.guild.domain.exception.ExceptionEntityResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.HttpException
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import io.javalin.core.JavalinConfig
import io.javalin.core.JavalinServer
import io.javalin.core.util.RouteOverviewUtil.metaInfo
import io.javalin.http.BadRequestResponse
import io.javalin.http.HttpResponseException
import io.javalin.plugin.json.JavalinJackson
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Component
class WebEntryPoint(private val guildController: GuildController) {

    fun init(): Javalin {
        JavalinJackson.configure(ObjectMapper())
        val application = Javalin.create { config ->
            config.requestLogger { context, ms ->
                Javalin.log.info("${context.method()} ${URLDecoder.decode(context.path(), StandardCharsets.UTF_8.name())} -> ${context.status()} took $ms ms ")
            }
        }

        application.routes {
            ApiBuilder.get("/api/guild/:realmSlug/:nameSlug/roster") { context ->
                guildController.getGuildRoster(context)
            }
        }

        application.exception(HttpResponseException::class.java) { e, context ->
            Javalin.log.error(e.stackTraceToString())
            context.status(e.status)
            context.json(ExceptionEntityResponse(e.status, e.localizedMessage))
        }

        return application;
    }

}
