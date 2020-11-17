package by.danilov.wow.guild.domain.request

import by.danilov.wow.guild.util.RequestEntityProvider.Companion.LOCALE
import by.danilov.wow.guild.util.RequestEntityProvider.Companion.LOCALE_DEFAULT_VALUE
import by.danilov.wow.guild.util.RequestEntityProvider.Companion.NAMESPACE
import by.danilov.wow.guild.util.RequestEntityProvider.Companion.REGION

data class BlizzardApiRequestEntity(
    var region: String,
    var namespace: String,
    var locale: String = LOCALE_DEFAULT_VALUE,
    var realmSlug: String = "{realmSlug}",
    var nameSlug: String = "{nameSlug}",
    var characterName: String = "{characterName}"
) {
    var queryParameters = listOf(REGION to region, NAMESPACE to namespace, LOCALE to locale)
}
