package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day14Test {

    @Test
    fun part1() {
        val output = Day14.part1(readInput("Day14"))

        assertEquals(136, output)
    }

    @Test
    fun part2() {
        val output = Day14.part2(readInput("Day14"))

        assertEquals(64, output)
    }

}