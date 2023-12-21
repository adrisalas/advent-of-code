package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day09Test {

    private val input = listOf(
        "0 3 6 9 12 15",
        "1 3 6 10 15 21",
        "10 13 16 21 30 45"
    )

    @Test
    fun part1() {
        val output = Day09.part1(input)

        assertEquals(114, output)
    }

    @Test
    fun part2() {
        val output = Day09.part2(input)

        assertEquals(2, output)
    }
}