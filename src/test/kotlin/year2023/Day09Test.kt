package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day09Test {

    @Test
    fun part1() {
        val output = Day09.part1(readInput("Day09"))

        assertEquals(114, output)
    }

    @Test
    fun part2() {
        val output = Day09.part2(readInput("Day09"))

        assertEquals(2, output)
    }
}