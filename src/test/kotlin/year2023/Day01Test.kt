package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day01Test {

    @Test
    fun part1() {
        val input = listOf(
            "1abc2",
            "pqr3stu8vwx",
            "a1b2c3d4e5f",
            "treb7uchet"
        )

        val output = Day01.part1(input)

        assertEquals(142, output)
    }

    @Test
    fun part2() {
        val input = listOf(
            "two1nine",
            "eightwothree",
            "abcone2threexyz",
            "xtwone3four",
            "4nineeightseven2",
            "zoneight234",
            "7pqrstsixteen",
        )

        val output = Day01.part2(input)

        assertEquals(281, output)
    }
}