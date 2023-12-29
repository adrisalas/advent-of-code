package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day21Test {

    private val inputTest = listOf(
        "...........",
        ".....###.#.",
        ".###.##..#.",
        "..#.#...#..",
        "....#.#....",
        ".##..S####.",
        ".##..#...#.",
        ".......##..",
        ".##.#.####.",
        ".##..##.##.",
        "..........."
    )

    @Test
    fun part1() {
        val output = Day21.part1(inputTest)

        assertEquals(2_665, output)
    }

    @Test
    fun part2() {
        val output = Day21.part2(inputTest)

        assertEquals(468_903_824_793_172L, output)
    }

}