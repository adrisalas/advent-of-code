package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import year2023.Day05.normalize
import year2023.Day05.removeRanges
import kotlin.test.Test

class Day05Test {

    private val input = """
        seeds: 79 14 55 13

        seed-to-soil map:
        50 98 2
        52 50 48

        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15

        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4

        water-to-light map:
        88 18 7
        18 25 70

        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13

        temperature-to-humidity map:
        0 69 1
        1 0 69

        humidity-to-location map:
        60 56 37
        56 93 4
    """.trimIndent()

    @Test
    fun part1() {
        val output = Day05.part1(input)

        assertEquals(35, output)
    }

    @Test
    fun part2() {
        val output = Day05.part2(input)

        assertEquals(46, output)
    }

    @Test
    fun sanitizeRanges() {
        val originalList = listOf(Pair(5L, 5L), Pair(6L, 8L), Pair(7L, 9L), Pair(9L, 10L), Pair(100L, 100L))
        val expected = listOf(Pair(5L, 10L), Pair(100L, 100L))

        val withMergedRanges = originalList.normalize()

        assertEquals(expected.size, withMergedRanges.size)
        assertEquals(expected[0], withMergedRanges[0])
        assertEquals(expected[1], withMergedRanges[1])
    }

    @Test
    fun removeRanges() {
        val originalList = listOf(Pair(1L, 2L), Pair(10L, 20L), Pair(30L, 40L))
        val toRemove = listOf(Pair(14L, 17L))
        val expected = listOf(Pair(1L, 2L), Pair(10L, 13L), Pair(18L, 20L), Pair(30L, 40L))

        val withMergedRanges = originalList.removeRanges(toRemove).normalize()
        println(withMergedRanges)

        assertEquals(expected.size, withMergedRanges.size)
        assertEquals(expected[0], withMergedRanges[0])
        assertEquals(expected[1], withMergedRanges[1])
        assertEquals(expected[2], withMergedRanges[2])
        assertEquals(expected[3], withMergedRanges[3])
    }
}