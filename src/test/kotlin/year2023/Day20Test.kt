package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day20Test {

    private val inputTest = listOf(
        "broadcaster -> a, b, c",
        "%a -> b",
        "%b -> c",
        "%c -> inv",
        "&inv -> a",
    )

    @Test
    fun part1() {
        val output = Day20.part1(inputTest)

        assertEquals(32_000_000, output)
    }

}