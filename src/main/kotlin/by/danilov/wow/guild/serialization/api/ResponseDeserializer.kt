package by.danilov.wow.guild.serialization.api

interface ResponseDeserializer<T> {
    fun deserialize(content: String): T?
}