package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day06Test {

    private val input = listOf(
        "Time:      7  15   30",
        "Distance:  9  40  200"
    )

    @Test
    fun part1() {
        val output = Day06.part1(input)

        assertEquals(288, output)
    }

    @Test
    fun part2() {
        val output = Day06.part2(input)

        assertEquals(71503, output)
    }
}