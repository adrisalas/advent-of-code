package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day13Test {

    private val input = """
        #.##..##.
        ..#.##.#.
        ##......#
        ##......#
        ..#.##.#.
        ..##..##.
        #.#.##.#.

        #...##..#
        #....#..#
        ..##..###
        #####.##.
        #####.##.
        ..##..###
        #....#..#
    """.trimIndent()

    @Test
    fun part1() {
        val output = Day13.part1(input)

        assertEquals(405, output)
    }

    @Test
    fun part2() {
        val output = Day13.part2(input)

        assertEquals(400, output)
    }

}