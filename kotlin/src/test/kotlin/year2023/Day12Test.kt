package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day12Test {

    private val input = listOf(
        "???.### 1,1,3",
        ".??..??...?##. 1,1,3",
        "?#?#?#?#?#?#?#? 1,3,1,6",
        "????.#...#... 4,1,1",
        "????.######..#####. 1,6,5",
        "?###???????? 3,2,1"
    )

    @Test
    fun part1() {
        val output = Day12.part1(input)

        assertEquals(21, output)
    }

    @Test
    fun part2() {
        val output = Day12.part2(input)

        assertEquals(525152, output)
    }

}