package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day15Test {

    @Test
    fun part1() {
        val output = Day15.part1(readInput2("Day15"))

        assertEquals(1320, output)
    }

    @Test
    fun part2() {
        val output = Day15.part2(readInput2("Day15"))

        assertEquals(145, output)
    }

}