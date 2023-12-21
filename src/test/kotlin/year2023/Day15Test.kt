package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day15Test {

    private val input = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"

    @Test
    fun part1() {
        val output = Day15.part1(input)

        assertEquals(1320, output)
    }

    @Test
    fun part2() {
        val output = Day15.part2(input)

        assertEquals(145, output)
    }

}