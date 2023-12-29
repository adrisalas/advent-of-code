package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day16Test {

    private val input = listOf(
        ".|...\\....",
        "|.-.\\.....",
        ".....|-...",
        "........|.",
        "..........",
        ".........\\",
        "..../.\\\\..",
        ".-.-/..|..",
        ".|....-|.\\",
        "..//.|...."
    )

    @Test
    fun part1() {
        val output = Day16.part1(input)

        assertEquals(46, output)
    }

    @Test
    fun part2() {
        val output = Day16.part2(input)

        assertEquals(51, output)
    }

}