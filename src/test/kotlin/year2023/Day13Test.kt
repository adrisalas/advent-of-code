package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day13Test {

    @Test
    fun part1() {
        val output = Day13.part1(readInput2("Day13"))

        assertEquals(405, output)
    }

    @Test
    fun part2() {
        val output = Day13.part2(readInput2("Day13"))

        assertEquals(400, output)
    }

}