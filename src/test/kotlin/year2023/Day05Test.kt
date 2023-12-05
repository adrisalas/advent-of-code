package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import year2023.Day05.removeRanges
import year2023.Day05.sanitizeRanges
import kotlin.test.Test

class Day05Test {

    @Test
    fun part1() {
        val output = Day05.part1(readInput("Day05"))

        assertEquals(35, output)
    }

    @Test
    fun part2() {
        val output = Day05.part2(readInput("Day05"))

        assertEquals(46, output)
    }

    @Test
    fun sanitizeRanges() {
        val originalList = listOf(Pair(5L, 5L), Pair(6L, 8L), Pair(7L, 9L), Pair(9L, 10L), Pair(100L, 100L))
        val expected = listOf(Pair(5L, 10L), Pair(100L, 100L))

        val withMergedRanges = originalList.sanitizeRanges()

        assertEquals(expected.size, withMergedRanges.size)
        assertEquals(expected[0], withMergedRanges[0])
        assertEquals(expected[1], withMergedRanges[1])
    }

    @Test
    fun removeRanges() {
        val originalList = listOf(Pair(1L, 2L), Pair(10L, 20L), Pair(30L, 40L))
        val toRemove = listOf(Pair(14L, 17L))
        val expected = listOf(Pair(1L, 2L), Pair(10L, 13L), Pair(18L, 20L), Pair(30L, 40L))

        val withMergedRanges = originalList.removeRanges(toRemove).sanitizeRanges()
        println(withMergedRanges)

        assertEquals(expected.size, withMergedRanges.size)
        assertEquals(expected[0], withMergedRanges[0])
        assertEquals(expected[1], withMergedRanges[1])
        assertEquals(expected[2], withMergedRanges[2])
        assertEquals(expected[3], withMergedRanges[3])
    }
}