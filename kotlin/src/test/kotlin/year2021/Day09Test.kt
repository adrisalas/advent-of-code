package year2021

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day09Test {

    @Test
    fun part1() {
        val input = listOf(
            "2199943210",
            "3987894921",
            "9856789892",
            "8767896789",
            "9899965678"
        )

        val output = Day09.part1(input)

        assertEquals(15, output)
    }

    @Test
    fun part2() {
        val input = listOf(
            "2199943210",
            "3987894921",
            "9856789892",
            "8767896789",
            "9899965678"
        )

        val output = Day09.part2(input)

        assertEquals(1134, output)
    }
}