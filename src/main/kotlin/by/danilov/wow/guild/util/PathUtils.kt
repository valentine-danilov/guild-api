package by.danilov.wow.guild.util

class PathUtils {
    companion object {
        fun getRelativePath(absolutePath: String): String {
            return absolutePath.replace(Regex("https?://[^/]+"), "")
                .ifEmpty { "/" }
        }
    }
}