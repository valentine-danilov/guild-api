package by.danilov.wow.guild.service.blizzard.processor

import by.danilov.wow.guild.properties.BlizzardApiConfigProperties
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import kotlin.IllegalArgumentException

@Component
class ProcessingConfiguration(private val blizzardApiConfigProperties: BlizzardApiConfigProperties) {

    private lateinit var processingConfiguration: Map<ProcessingType, List<String>>

    @PostConstruct
    fun setUp() {
        processingConfiguration = blizzardApiConfigProperties.processConfig
            .mapKeys { ProcessingType.of(it.key) }
    }

    fun getConfigurationByType(processingType: ProcessingType): List<String> {
        return processingConfiguration[processingType]
            ?: throw IllegalArgumentException("No such ProcessingType configured: $processingType")
    }

}