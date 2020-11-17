package by.danilov.wow.guild.util

import by.danilov.wow.guild.domain.request.BlizzardApiRequestEntity
import io.javalin.http.Context

class RequestEntityProvider {
    companion object {

        val REGION = ":region"
        val NAMESPACE = "namespace"
        val LOCALE = "locale"
        val LOCALE_DEFAULT_VALUE = "en_US"

        fun contextToRequestEntity(context: Context) : BlizzardApiRequestEntity{
            val region = context.queryParam<String>(REGION).get()
            val namespace = context.queryParam<String>(NAMESPACE).get()
            val locale = context.queryParam<String>(LOCALE, LOCALE_DEFAULT_VALUE)
                .check({it.matches(Regex("[a-z]{2}_[A-Z]{2}"))}, "Locale doesn't match '[a-z]{2}_[A-Z]{2}' pattern")
                .get()
            return BlizzardApiRequestEntity(region, namespace, locale)
        }
    }
}
