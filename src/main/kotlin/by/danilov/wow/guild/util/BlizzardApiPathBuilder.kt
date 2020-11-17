package by.danilov.wow.guild.util

class BlizzardApiPathBuilder(private var apiHost: String) {

    private var url: String = ""

    companion object {
        fun builder(apiHost: String): BlizzardApiPathBuilder {
            return BlizzardApiPathBuilder.builder(apiHost)
        }
    }

    fun guildRoster(realmSlug: String, nameSlug: String): String {
        return this.data().guild(realmSlug, nameSlug).roster()
            .build()
    }

    fun characterIndex(realmSlug: String, characterName: String): String {
        return this.profileWow().character(realmSlug, characterName)
            .build()
    }

    fun data(): BlizzardApiPathBuilder {
        url += "/data/wow"
        return this
    }

    fun profileWow(): BlizzardApiPathBuilder {
        url += "/profile/wow"
        return this
    }

    fun profileUser(): BlizzardApiPathBuilder {
        url += "/profile/user"
        return this
    }

    fun guild(realmSlug: String, nameSlug: String): BlizzardApiPathBuilder {
        url += "/guild/$realmSlug/$nameSlug"
        return this
    }

    fun roster(): BlizzardApiPathBuilder {
        url += "/roster"
        return this
    }

    fun character(realmSlug: String, characterName: String): BlizzardApiPathBuilder {
        url += "/character/$realmSlug/$characterName"
        return this
    }

    fun build(): String {
        return this.apiHost.plus(this.url)
    }
}
