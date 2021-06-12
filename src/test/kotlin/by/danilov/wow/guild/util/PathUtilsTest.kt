package by.danilov.wow.guild.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

internal class PathUtilsTest {

    @ParameterizedTest
    @CsvSource(value = [
        "https://example.com/relative/path,/relative/path",
        "http://example.com/relative/path,/relative/path",
        "https://example.com,/"
    ])
    fun testGetRelativePath(input: String, expected: String) {
        assertEquals(PathUtils.getRelativePath(input), expected)
    }
}