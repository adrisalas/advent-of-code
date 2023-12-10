package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day08Test {

    val part2Input = listOf(
        "LR",
        "",
        "11A = (11B, XXX)",
        "11B = (XXX, 11Z)",
        "11Z = (11B, XXX)",
        "22A = (22B, XXX)",
        "22B = (22C, 22C)",
        "22C = (22Z, 22Z)",
        "22Z = (22B, 22B)",
        "XXX = (XXX, XXX)"
    )

    @Test
    fun part1() {
        val output = Day08.part1(readInput("Day08"))

        assertEquals(6, output)
    }

    @Test
    fun part2() {
        val output = Day08.part2(part2Input)

        assertEquals(6, output)
    }
}