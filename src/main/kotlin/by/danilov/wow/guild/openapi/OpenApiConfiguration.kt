package by.danilov.wow.guild.openapi

import by.danilov.wow.guild.domain.exception.ExceptionEntityResponse
import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.OpenApiPlugin
import io.javalin.plugin.openapi.ui.SwaggerOptions
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class OpenApiConfiguration {

    @Bean
    open fun openApiOptions(): OpenApiOptions = OpenApiOptions(Info().version("1.0").description("Guild API"))
        .path("/swagger-docs")
        .swagger(SwaggerOptions("/swagger-ui").title("Guild API"))
        .activateAnnotationScanningFor("by.danilov.wow.guild.controller")
        .defaultDocumentation {
            it.json("400", ExceptionEntityResponse::class.java)
            it.json("404", ExceptionEntityResponse::class.java)
            it.json("500", ExceptionEntityResponse::class.java)
            it.json("503", ExceptionEntityResponse::class.java)
        }

    @Bean
    open fun openApiPlugin(openApiOptions: OpenApiOptions): OpenApiPlugin = OpenApiPlugin(openApiOptions)
}