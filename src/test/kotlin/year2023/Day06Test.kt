package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day06Test {

    @Test
    fun part1() {
        val output = Day06.part1(readInput("Day06"))

        assertEquals(288, output)
    }

    @Test
    fun part2() {
        val output = Day06.part2(readInput("Day06"))

        assertEquals(71503, output)
    }
}