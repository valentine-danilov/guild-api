package by.danilov.wow.guild.service.api.blizzard.processor

import by.danilov.wow.guild.service.blizzard.processor.ProcessingType

interface BlizzardApiProcessorService {
    fun process(content: MutableMap<String, Any>, locale: String,  processingType: ProcessingType = ProcessingType.CHARACTER)
}
