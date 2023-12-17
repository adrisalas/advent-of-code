package year2023

import year2023.Day16.Direction.*

fun main() {
    val input = readInput("Day16")
    Day16.part1(input).println()
    Day16.part2(input).println()
}

object Day16 {
    fun part1(input: List<String>): Int {
        val contraption = input.map { line -> line.map { Box(it) } }

        contraption.energize(Point(0, 0), RIGHT)

        return contraption.countEnergized()
    }

    fun part2(input: List<String>): Int {
        var maxEnergized = 0

        for (i in input[0].indices) {
            val contraption = input.map { line -> line.map { Box(it) } }

            contraption.energize(Point(0, i), DOWN)
            val energized = contraption.countEnergized()
            if (energized > maxEnergized) {
                maxEnergized = energized
            }
        }

        for (i in input[0].indices) {
            val contraption = input.map { line -> line.map { Box(it) } }

            contraption.energize(Point(input.size - 1, i), UP)
            val energized = contraption.countEnergized()
            if (energized > maxEnergized) {
                maxEnergized = energized
            }
        }

        for (i in input.indices) {
            val contraption = input.map { line -> line.map { Box(it) } }

            contraption.energize(Point(i, 0), RIGHT)
            val energized = contraption.countEnergized()
            if (energized > maxEnergized) {
                maxEnergized = energized
            }
        }

        for (i in input.indices) {
            val contraption = input.map { line -> line.map { Box(it) } }

            contraption.energize(Point(i, input[0].length - 1), LEFT)
            val energized = contraption.countEnergized()
            if (energized > maxEnergized) {
                maxEnergized = energized
            }
        }

        return maxEnergized
    }

    private enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private data class Box(val value: Char, val energized: MutableSet<Direction> = mutableSetOf()) {
        fun reflect(beamDirection: Direction): List<Direction> {
            return when (value) {
                '.' -> listOf(beamDirection)
                '-' -> when (beamDirection) {
                    UP, DOWN -> listOf(LEFT, RIGHT)
                    LEFT, RIGHT -> listOf(beamDirection)
                }

                '|' -> when (beamDirection) {
                    UP, DOWN -> listOf(beamDirection)
                    LEFT, RIGHT -> listOf(UP, DOWN)
                }

                '/' -> when (beamDirection) {
                    UP -> listOf(RIGHT)
                    DOWN -> listOf(LEFT)
                    LEFT -> listOf(DOWN)
                    RIGHT -> listOf(UP)
                }

                '\\' -> when (beamDirection) {
                    UP -> listOf(LEFT)
                    DOWN -> listOf(RIGHT)
                    LEFT -> listOf(UP)
                    RIGHT -> listOf(DOWN)
                }

                else -> error("Invalid value")
            }
        }
    }

    private data class Point(val row: Int, val column: Int) {
        fun to(direction: Direction): Point {
            return when (direction) {
                UP -> Point(this.row - 1, this.column)
                DOWN -> Point(this.row + 1, this.column)
                LEFT -> Point(this.row, this.column - 1)
                RIGHT -> Point(this.row, this.column + 1)
            }
        }
    }

    private fun List<List<Box>>.energize(point: Point, beamDirection: Direction) {
        if (point.row < 0 || point.column < 0
            || point.row >= this.size || point.column >= this[point.row].size
        ) {
            return
        }

        val box = this[point.row][point.column]

        if (beamDirection in box.energized) {
            return
        }

        box.energized.add(beamDirection)

        val reflectedBeams = box.reflect(beamDirection)

        reflectedBeams.forEach {
            val reflectedBeam = it
            val newPoint = point.to(reflectedBeam)
            this.energize(newPoint, reflectedBeam)
        }

    }

    private fun List<List<Box>>.countEnergized(): Int {
        return this.sumOf { line -> line.count { it.energized.isNotEmpty() } }
    }
}


