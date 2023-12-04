package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day04Test {

    @Test
    fun part1() {
        val output = Day04.part1(readInput("Day04"))

        assertEquals(13, output)
    }

    @Test
    fun part2() {
        val output = Day04.part2(readInput("Day04"))

        assertEquals(30, output)
    }
}