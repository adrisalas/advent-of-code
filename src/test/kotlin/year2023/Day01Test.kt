package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day01Test {

    @Test
    fun part1() {
        val output = Day01.part1(readInput("Day01_P1"))

        assertEquals(142, output)
    }

    @Test
    fun part2() {
        val output = Day01.part2(readInput("Day01_P2"))

        assertEquals(281, output)
    }
}