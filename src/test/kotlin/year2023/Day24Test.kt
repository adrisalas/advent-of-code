package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day24Test {

    private val inputTest = listOf(
        "19, 13, 30 @ -2,  1, -2",
        "18, 19, 22 @ -1, -1, -2",
        "20, 25, 34 @ -2, -2, -4",
        "12, 31, 28 @ -1, -2, -1",
        "20, 19, 15 @  1, -5, -3",
    )

    @Test
    fun part1() {
        val output = Day24.part1(inputTest, 7, 27)

        assertEquals(2, output)
    }

}