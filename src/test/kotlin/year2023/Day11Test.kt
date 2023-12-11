package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day11Test {

    @Test
    fun part1() {
        val output = Day11.part1(readInput("Day11"))

        assertEquals(374, output)
    }

    @Test
    fun expandGalaxy() {
        val expandedGalaxy = Day11.expandGalaxy(readInput("Day11"))
        val expectedGalaxy = listOf(
            "....#........",
            ".........#...",
            "#............",
            ".............",
            ".............",
            "........#....",
            ".#...........",
            "............#",
            ".............",
            ".............",
            ".........#...",
            "#....#......."
        )

        assertEquals(expectedGalaxy, expandedGalaxy)
    }

    @Test
    fun part2() {
        val output = Day11.part2(readInput("Day11"))

        assertEquals(82000210L, output)
    }

    @Test
    fun expandGalaxyLikeReallyReallyBig() {
        val expandedGalaxy = Day11.expandGalaxyLikeReallyReallyBig(readInput("Day11"))
        val expectedGalaxy = listOf(
            ".. #. .. .",
            ".. .. .# .",
            "#. .. .. .",
            "          ",
            ".. .. #. .",
            ".# .. .. .",
            ".. .. .. #",
            "          ",
            ".. .. .# .",
            "#. .# .. ."
        )

        assertEquals(expectedGalaxy, expandedGalaxy)
    }
}