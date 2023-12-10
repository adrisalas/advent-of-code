package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day10Test {

    var firstLoop = listOf(
        ".....",
        ".S-7.",
        ".|.|.",
        ".L-J.",
        "....."
    )

    var secondLoop = listOf(
        "..F7.",
        ".FJ|.",
        "SJ.L7",
        "|F--J",
        "LJ..."
    )

    var thirdLoop = listOf(
        "...........",
        ".S-------7.",
        ".|F-----7|.",
        ".||.....||.",
        ".||.....||.",
        ".|L-7.F-J|.",
        ".|..|.|..|.",
        ".L--J.L--J.",
        "..........."
    )

    val fourthLoop = listOf(
        "FF7FSF7F7F7F7F7F---7",
        "L|LJ||||||||||||F--J",
        "FL-7LJLJ||||||LJL-77",
        "F--JF--7||LJLJ7F7FJ-",
        "L---JF-JLJ.||-FJLJJ7",
        "|F|F-JF---7F7-L7L|7|",
        "|FFJF7L7F-JF7|JL---7",
        "7-L-JL7||F7|L7F-7F7|",
        "L.L7LFJ|||||FJL7||LJ",
        "L7JLJL-JLJLJL--JLJ.L"
    )

    @Test
    fun part1() {
        val output = Day10.part1(firstLoop)

        assertEquals(4, output)
    }

    @Test
    fun part1_secondLoop() {
        val output = Day10.part1(secondLoop)

        assertEquals(8, output)
    }

    @Test
    fun part2() {
        val output = Day10.part2(thirdLoop)

        assertEquals(4, output)
    }

    @Test
    fun part2_fourthLoop() {
        val output = Day10.part2(fourthLoop)

        assertEquals(10, output)
    }
}