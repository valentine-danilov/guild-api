package by.danilov.wow.guild

import by.danilov.wow.guild.controller.GuildController
import com.fasterxml.jackson.databind.ObjectMapper
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import io.javalin.plugin.json.JavalinJackson
import org.springframework.stereotype.Component

@Component
class WebEntryPoint(val guildController: GuildController) {

    fun init() : Javalin {
        JavalinJackson.configure(ObjectMapper())
        val application = Javalin.create().apply {
            exception(Exception::class.java) {
                e, ctx -> e.printStackTrace()
            }
            error(404) {
                ctx -> ctx.json(mapOf("error" to "Not Found", "statusCode" to 404))
            }
            error(500) {
                ctx -> ctx.json(mapOf("error" to "Internal Server Error", "statusCode" to 500))
            }
        }

        application.routes {
            ApiBuilder.get("/api/guild/:realmSlug/:nameSlug/roster") {
                context -> guildController.getGuildRoster(context)
            }
        }

        return application;
    }

}