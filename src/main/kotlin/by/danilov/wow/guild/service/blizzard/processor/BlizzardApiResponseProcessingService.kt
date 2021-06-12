package by.danilov.wow.guild.service.blizzard.processor

import by.danilov.wow.guild.client.BlizzardApiFuelClient
import by.danilov.wow.guild.properties.BlizzardApiConfigProperties
import by.danilov.wow.guild.service.api.blizzard.processor.BlizzardApiProcessorService
import kotlinx.coroutines.*
import org.springframework.stereotype.Service

@Service
class BlizzardApiResponseProcessingService(
    private var blizzardApiFuelClient: BlizzardApiFuelClient,
    private val processingConfiguration: ProcessingConfiguration
) : BlizzardApiProcessorService {

    override fun process(content: MutableMap<String, Any>, locale: String, processingType: ProcessingType) {
        runBlocking {
            withContext(Dispatchers.IO) {
                process(content, locale, processingType, this)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun process(
        content: MutableMap<String, Any>,
        locale: String,
        processingType: ProcessingType,
        coroutineScope: CoroutineScope,
    ) {
        content.entries.forEach {
            when {
                processingConfiguration.getConfigurationByType(processingType).contains(it.key) -> {
                    processIfConfigured(it, content, locale, processingType, coroutineScope)
                }
                it.value is List<*> -> {
                    processIfList(it.value as List<*>, locale, processingType, coroutineScope)
                }
                it.value is MutableMap<*, *> -> {
                    process(it.value as MutableMap<String, Any>, locale, processingType, coroutineScope)
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun processIfList(
        content: List<*>, locale: String,
        processingType: ProcessingType,
        coroutineScope: CoroutineScope,
    ) {
        content.forEach { listElem ->
            if (listElem is MutableMap<*, *>) {
                process(listElem as MutableMap<String, Any>, locale, processingType, coroutineScope)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun processIfConfigured(
        entry: MutableMap.MutableEntry<String, Any>,
        content: MutableMap<String, Any>,
        locale: String,
        processingType: ProcessingType,
        coroutineScope: CoroutineScope,
    ) {
        if (entry.value is MutableMap<*, *>) {
            getSubComponentHref(entry.value as MutableMap<String, Any>)?.let {
                coroutineScope.launch {
                    val response =
                        blizzardApiFuelClient.get(it, listOf("locale" to locale))
                    process(response, locale, processingType, coroutineScope)
                    content[entry.key] = response
                }
            }
        }
    }

    private fun getSubComponentHref(content: MutableMap<String, Any>): String? {
        val keyPart = content["key"]
        if (keyPart != null) {
            if (keyPart is MutableMap<*, *>) {
                val href = keyPart["href"]
                return if (href is String) href else null
            }
        } else {
            val href = content["href"]
            return if (href is String) href else null
        }
        return null
    }
}
