package by.danilov.wow.guild.domain.common

import com.fasterxml.jackson.annotation.JsonProperty

data class Key(
    @JsonProperty("href") val href: String
)