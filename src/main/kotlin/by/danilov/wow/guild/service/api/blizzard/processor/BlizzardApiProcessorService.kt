package by.danilov.wow.guild.service.api.blizzard.processor

interface BlizzardApiProcessorService {
    fun process(content: MutableMap<String, Any>, locale: String)
}
