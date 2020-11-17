package by.danilov.wow.guild.service.blizzard.processor

import by.danilov.wow.guild.serialization.MutableMapDeserializer
import by.danilov.wow.guild.service.api.blizzard.BlizzardApiClient
import by.danilov.wow.guild.service.api.blizzard.processor.BlizzardApiProcessorService
import by.danilov.wow.guild.util.RequestEntityProvider.Companion.LOCALE
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class BlizzardApiResponseProcessingService(
    private var blizzardApiClient: BlizzardApiClient,
    private var deserializer: MutableMapDeserializer
) : BlizzardApiProcessorService {

    @Value("#{'\${blizzard.api.process.config}'.split(',')}")
    private lateinit var processConfiguration: List<String>

    override fun process(content: MutableMap<String, Any>, locale: String) {
        runBlocking {
            withContext(Dispatchers.IO) {
                process(content, locale, this)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun process(content: MutableMap<String, Any>, locale: String, coroutineScope: CoroutineScope) {
        content.entries.forEach {
            when {
                it.value is List<*> -> {
                    processIfList(it.value as List<*>, locale, coroutineScope)
                }
                processConfiguration.contains(it.key) -> {
                    processIfConfigured(it, content, locale, coroutineScope)
                }
                it.value is MutableMap<*, *> -> {
                    process(it.value as MutableMap<String, Any>, locale, coroutineScope)
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun processIfList(content: List<*>, locale: String, coroutineScope: CoroutineScope) {
        content.forEach { listElem ->
            if (listElem is MutableMap<*, *>) {
                process(listElem as MutableMap<String, Any>, locale, coroutineScope)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun processIfConfigured(
        entry: MutableMap.MutableEntry<String, Any>,
        content: MutableMap<String, Any>,
        locale: String,
        coroutineScope: CoroutineScope
    ) {
        if (entry.value is MutableMap<*, *>) {
            getSubComponentHref(entry.value as MutableMap<String, Any>)?.let {
                coroutineScope.launch {
                    val response =
                        blizzardApiClient.authorizedGetRequest(
                            it, deserializer,
                            listOf(LOCALE to locale)
                        )
                    process(response, locale, coroutineScope)
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
