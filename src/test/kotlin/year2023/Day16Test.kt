package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day16Test {

    @Test
    fun part1() {
        val output = Day16.part1(readInput("Day16"))

        assertEquals(46, output)
    }

    @Test
    fun part2() {
        val output = Day16.part2(readInput("Day16"))

        assertEquals(51, output)
    }

}