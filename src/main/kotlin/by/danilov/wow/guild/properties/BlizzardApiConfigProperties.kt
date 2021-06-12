package by.danilov.wow.guild.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "blizzard.api")
data class BlizzardApiConfigProperties(
    var host: String,
    var processConfig: Map<String, List<String>>,
    var auth: AuthConfigProperties
)

data class AuthConfigProperties(
    var host: String,
    var clientId: String,
    var clientSecret: String
)