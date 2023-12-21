package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day11Test {
    private val input = listOf(
        "...#......",
        ".......#..",
        "#.........",
        "..........",
        "......#...",
        ".#........",
        ".........#",
        "..........",
        ".......#..",
        "#...#....."
    )

    @Test
    fun part1() {
        val output = Day11.part1(input)

        assertEquals(374, output)
    }

    @Test
    fun expandGalaxy() {
        val expandedGalaxy = Day11.expandGalaxy(input)
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
        val output = Day11.part2(input)

        assertEquals(82000210L, output)
    }

    @Test
    fun expandGalaxyLikeReallyReallyBig() {
        val expandedGalaxy = Day11.expandGalaxyLikeReallyReallyBig(input)
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