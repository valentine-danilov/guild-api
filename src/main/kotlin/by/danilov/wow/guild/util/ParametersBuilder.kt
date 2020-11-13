package by.danilov.wow.guild.util

import com.github.kittinunf.fuel.core.Parameters

class ParametersBuilder {
    companion object {
        val REGION = ":region"
        val NAMESPACE = "namespace"
        val LOCALE = "locale"

        fun getBlizzardApiParameters(region: String, namespace: String, locale: String): Parameters {
            return listOf(REGION to region, NAMESPACE to namespace, LOCALE to locale)
        }

        fun getBlizzardApiLocaleParameter(locale: String): Parameters {
            return listOf(LOCALE to locale)
        }
    }
}
