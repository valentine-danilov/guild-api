package by.danilov.wow.guild.service.blizzard.processor

enum class ProcessingType(private val type: String) {
    CHARACTER("character"),
    GUILD_ROSTER("guildRoster");

    companion object {
        fun of(type: String): ProcessingType {
            return values().toList().find { it.type == type }
                ?: throw IllegalArgumentException("No ProcessingType enum value for type \"${type}\"")
        }
    }
}