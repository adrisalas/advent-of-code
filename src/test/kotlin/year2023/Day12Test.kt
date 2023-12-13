package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day12Test {

    @Test
    fun part1() {
        val output = Day12.part1(readInput("Day12"))

        assertEquals(21, output)
    }

    @Test
    fun part2() {
        val output = Day12.part2(readInput("Day12"))

        assertEquals(525152, output)
    }

}