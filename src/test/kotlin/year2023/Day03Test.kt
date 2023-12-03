package year2023

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day03Test {

    @Test
    fun part1() {
        val output = Day03.part1(readInput("Day03"))

        assertEquals(4361, output)
    }

    @Test
    fun part2() {
        val output = Day03.part2(readInput("Day03"))

        assertEquals(467835, output)
    }

    @Test
    fun encapsulateMatrix() {
        val originalMatrix = listOf("1")

        val matrix = Day03.encapsulateMatrixWithDots(originalMatrix)

        assertEquals(3, matrix.size)
        assertEquals("...", matrix[0])
        assertEquals(".1.", matrix[1])
        assertEquals("...", matrix[2])
    }

    @Test
    fun findPointAtDot() {
        val matrix = Day03.encapsulateMatrixWithDots(readInput("Day03"))

        val point = Day03.findPointAt(matrix, 0, 0)

        assertEquals(null, point)
        assertEquals('.', matrix[0][0])
    }

    @Test
    fun findPointAtSymbol() {
        val matrix = Day03.encapsulateMatrixWithDots(readInput("Day03"))
        val row = 2
        val column = 4

        val point = Day03.findPointAt(matrix, row, column)

        assertEquals(null, point)
        assertEquals('*', matrix[row][column])
    }

    @Test
    fun findPointAtNumber() {
        val matrix = Day03.encapsulateMatrixWithDots(readInput("Day03"))
        val row = 1
        val column = 1

        val point = Day03.findPointAt(matrix, row, column)

        assertEquals(467, point?.value)
        assertEquals('4', matrix[row][column])
        assertEquals('6', matrix[row][column + 1])
        assertEquals('7', matrix[row][column + 2])
        assertEquals(point, Day03.findPointAt(matrix, row, column + 1))
        assertEquals(point, Day03.findPointAt(matrix, row, column + 2))
    }

    @Test
    fun `find adjacent points in corners`() {
        val matrix = Day03.encapsulateMatrixWithDots(
            listOf(
                "1.2",
                ".*.",
                "3.4"
            )
        )

        val points = Day03.findAdjacentPointsTo(matrix, 2, 2)

        assertEquals(4, points.size)
        val sortedPoints = points.sortedBy { it.value }
        assertEquals(Day03.Point(1, 1, 1), sortedPoints[0])
        assertEquals(Day03.Point(1, 3, 2), sortedPoints[1])
        assertEquals(Day03.Point(3, 1, 3), sortedPoints[2])
        assertEquals(Day03.Point(3, 3, 4), sortedPoints[3])
    }

    @Test
    fun `find adjacent points in cross`() {
        val matrix = Day03.encapsulateMatrixWithDots(
            listOf(
                ".12",
                ".*.",
                "34."
            )
        )

        val points = Day03.findAdjacentPointsTo(matrix, 2, 2)

        assertEquals(2, points.size)
        val sortedPoints = points.sortedBy { it.value }
        assertEquals(Day03.Point(1, 2, 12), sortedPoints[0])
        assertEquals(Day03.Point(3, 1, 34), sortedPoints[1])
    }
}