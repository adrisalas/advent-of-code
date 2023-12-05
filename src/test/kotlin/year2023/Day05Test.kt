package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day05Test {

    @Test
    fun part1() {
        val output = Day05.part1(readInput("Day05"))

        assertEquals(35, output)
    }

    @Test
    fun part2() {
        val output = Day05.part2(readInput("Day05"))

        assertEquals(46, output)
    }
}