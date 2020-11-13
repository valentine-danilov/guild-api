package by.danilov.wow.guild.domain.exception

import com.fasterxml.jackson.annotation.JsonProperty

data class ExceptionEntityResponse(
    @JsonProperty("status") val status: Int,
    @JsonProperty("error") val error: String
)
