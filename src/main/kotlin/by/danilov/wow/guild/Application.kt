package by.danilov.wow.guild

import io.javalin.Javalin
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class Application(webEntryPoint: WebEntryPoint) {
    init {
        val javalin = webEntryPoint.init()
        javalin.start(7000)
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
