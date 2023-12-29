package year2023

fun main() {
    val input = readInput("Day03")
    Day03.part1(input).println()
    Day03.part2(input).println()
}

object Day03 {
    data class Point(val row: Int, val column: Int, val value: Int)

    fun part1(input: List<String>): Int {
        val matrix = encapsulateMatrixWithDots(input)

        return matrix
            .flatMapIndexed { rowIndex, row ->
                row.flatMapIndexed { columnIndex, cell ->
                    when {
                        !cell.isDigit() && cell != '.' -> findAdjacentPointsTo(matrix, rowIndex, columnIndex)
                        else -> setOf()
                    }
                }
            }
            .toSet()
            .sumOf { it.value }
    }

    fun part2(input: List<String>): Int {
        val matrix = encapsulateMatrixWithDots(input)

        return matrix
            .flatMapIndexed { rowIndex, row ->
                row.mapIndexed { columnIndex, cell ->
                    if (cell != '*') {
                        return@mapIndexed 0
                    }
                    val points = findAdjacentPointsTo(matrix, rowIndex, columnIndex)
                    if (points.size != 2) {
                        return@mapIndexed 0
                    }
                    return@mapIndexed points.first().value * points.last().value
                }
            }
            .sum()
    }

    /**
     * This way we should not care about "searching" outside the limits of the matrix,
     * as we will find a '.' in the border. Not efficient for a large dataset, but enough for this problem
     */
    fun encapsulateMatrixWithDots(matrix: List<String>): List<String> {
        val newLineSize = matrix.first().length + 2

        return listOf(
            CharArray(newLineSize) { '.' }.joinToString(""),
            *matrix.map { ".$it." }.toTypedArray(),
            CharArray(newLineSize) { '.' }.joinToString("")
        )
    }

    fun findPointAt(matrix: List<String>, row: Int, column: Int): Point? {
        val cell = matrix[row][column]
        if (!cell.isDigit()) {
            return null
        }

        var beginningColumn = column
        while (matrix[row][beginningColumn - 1].isDigit()) {
            beginningColumn--
        }

        val value = "[0-9]+".toRegex()
            .find(matrix[row], beginningColumn)
            ?.value
            ?.toInt()
            ?: 0

        return Point(row, beginningColumn, value)
    }

    fun findAdjacentPointsTo(matrix: List<String>, row: Int, column: Int): Set<Point> {
        val points = mutableSetOf<Point>()

        val westPoint = findPointAt(matrix, row, column - 1)
        if (westPoint != null) points.add(westPoint)

        val eastPoint = findPointAt(matrix, row, column + 1)
        if (eastPoint != null) points.add(eastPoint)

        val northPoint = findPointAt(matrix, row - 1, column)
        if (northPoint != null) {
            points.add(northPoint)
        } else {
            val northWestPoint = findPointAt(matrix, row - 1, column - 1)
            if (northWestPoint != null) points.add(northWestPoint)
            val northEastPoint = findPointAt(matrix, row - 1, column + 1)
            if (northEastPoint != null) points.add(northEastPoint)
        }

        val southPoint = findPointAt(matrix, row + 1, column)
        if (southPoint != null) {
            points.add(southPoint)
        } else {
            val southWestPoint = findPointAt(matrix, row + 1, column - 1)
            if (southWestPoint != null) points.add(southWestPoint)
            val southEastPoint = findPointAt(matrix, row + 1, column + 1)
            if (southEastPoint != null) points.add(southEastPoint)
        }

        return points
    }
}