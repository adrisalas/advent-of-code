package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day07Test {

    @Test
    fun part1() {
        val output = Day07.part1(readInput("Day07"))

        assertEquals(6440, output)
    }

    @Test
    fun part2() {
        val output = Day07.part2(readInput("Day07"))

        assertEquals(5905, output)
    }
}