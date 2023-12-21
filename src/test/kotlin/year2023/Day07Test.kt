package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day07Test {

    private val input = listOf(
        "32T3K 765",
        "T55J5 684",
        "KK677 28",
        "KTJJT 220",
        "QQQJA 483"
    )

    @Test
    fun part1() {
        val output = Day07.part1(input)

        assertEquals(6440, output)
    }

    @Test
    fun part2() {
        val output = Day07.part2(input)

        assertEquals(5905, output)
    }
}