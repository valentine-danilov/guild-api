package by.danilov.wow.guild

import by.danilov.wow.guild.properties.BlizzardApiConfigProperties
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableFeignClients
@EnableConfigurationProperties(BlizzardApiConfigProperties::class)
open class Application(webEntryPoint: WebEntryPoint) {
    init {
        val applicationServer = webEntryPoint.init()
        applicationServer.start(7000)
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
