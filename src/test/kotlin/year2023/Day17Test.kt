package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day17Test {

    val inputTest = listOf(
        "2413432311323",
        "3215453535623",
        "3255245654254",
        "3446585845452",
        "4546657867536",
        "1438598798454",
        "4457876987766",
        "3637877979653",
        "4654967986887",
        "4564679986453",
        "1224686865563",
        "2546548887735",
        "4322674655533"
    )

    @Test
    fun part1() {
        val output = Day17.part1(inputTest)

        assertEquals(102, output)
    }

    @Test
    fun part2() {
        val output = Day17.part2(inputTest)

        assertEquals(94, output)
    }

}