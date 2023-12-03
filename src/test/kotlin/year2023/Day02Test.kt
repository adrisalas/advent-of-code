package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day02Test {

    @Test
    fun part1() {
        val output = Day02.part1(readInput("Day02"))

        assertEquals(8, output)
    }

    @Test
    fun part2() {
        val output = Day02.part2(readInput("Day02"))

        assertEquals(2286, output)
    }
}