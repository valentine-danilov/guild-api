package by.danilov.wow.guild

import by.danilov.wow.guild.controller.CharacterProfileController
import by.danilov.wow.guild.controller.GuildApiController
import by.danilov.wow.guild.domain.exception.ExceptionEntityResponse
import com.fasterxml.jackson.databind.ObjectMapper
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.core.plugin.Plugin
import io.javalin.http.HttpResponseException
import io.javalin.plugin.json.JavalinJackson
import io.micrometer.prometheus.PrometheusMeterRegistry
import io.prometheus.client.exporter.common.TextFormat
import org.springframework.stereotype.Component
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Component
class WebEntryPoint(
    private val guildApiController: GuildApiController,
    private val characterProfileController: CharacterProfileController,
    private val plugins: List<Plugin>,
    private val prometheusRegistry: PrometheusMeterRegistry
) {

    fun init(): Javalin {
        JavalinJackson.configure(ObjectMapper())
        val application = Javalin.create { config ->
            plugins.forEach { config.registerPlugin(it) }
            config.requestLogger { context, ms ->
                Javalin.log.info(
                    "${context.method()} ${
                        URLDecoder.decode(
                            context.path(),
                            StandardCharsets.UTF_8.name()
                        )
                    } -> ${context.status()} took $ms ms "
                )
            }
        }

        application.routes {
            get("/api/guild/:realmSlug/:nameSlug/roster") { context ->
                guildApiController.getGuildRoster(context)
            }
            get("/api/profile/character/:realmSlug/:characterName") { context ->
                characterProfileController.getCharacterProfileSummary(context)
            }
            get("/prometheus") { context ->
                context.contentType(TextFormat.CONTENT_TYPE_004)
                    .result(prometheusRegistry.scrape())
            }
        }

        application.exception(HttpResponseException::class.java) { e, context ->
            Javalin.log.error(e.stackTraceToString())
            context.status(e.status)
            context.json(ExceptionEntityResponse(e.status, e.localizedMessage))
        }

        application.exception(Exception::class.java) { e, context ->
            Javalin.log.error(e.stackTraceToString())
            context.status(500)
            context.json(ExceptionEntityResponse(500, "Internal Server Error"))
        }

        return application;
    }

}
