package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day22Test {

    private val inputTest = listOf(
        "1,0,1~1,2,1",
        "0,0,2~2,0,2",
        "0,2,3~2,2,3",
        "0,0,4~0,2,4",
        "2,0,5~2,2,5",
        "0,1,6~2,1,6",
        "1,1,8~1,1,9",
    )

    @Test
    fun part1() {
        val output = Day22.part1(inputTest)

        assertEquals(5, output)
    }

    @Test
    fun part2() {
        val output = Day22.part2(inputTest)

        assertEquals(7, output)
    }

}