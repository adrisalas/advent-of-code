package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day14Test {

    private val input = listOf(
        "O....#....",
        "O.OO#....#",
        ".....##...",
        "OO.#O....O",
        ".O.....O#.",
        "O.#..O.#.#",
        "..O..#O..O",
        ".......O..",
        "#....###..",
        "#OO..#...."
    )

    @Test
    fun part1() {
        val output = Day14.part1(input)

        assertEquals(136, output)
    }

    @Test
    fun part2() {
        val output = Day14.part2(input)

        assertEquals(64, output)
    }

}