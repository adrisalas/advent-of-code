package year2023

import year2023.Day17.Direction.*
import java.util.*

fun main() {
    val input = readInput("Day17")
    Day17.part1(input).println()
    Day17.part2(input).println()
}

object Day17 {
    fun part1(input: List<String>): Int {
        val heatMap = input.map { row ->
            row.map { it.digitToInt() }
        }
        return heatMap.findMinHeatLoss(
            minSteps = 1,
            maxSteps = 3
        )
    }


    fun part2(input: List<String>): Int {
        val heatMap = input.map { row ->
            row.map { it.digitToInt() }
        }
        return heatMap.findMinHeatLoss(
            minSteps = 4,
            maxSteps = 10
        )
    }

    private fun List<List<Int>>.findMinHeatLoss(minSteps: Int, maxSteps: Int): Int {
        val endRow = this.lastIndex
        val endColumn = this.first().lastIndex

        val visited = mutableMapOf<Location, Int>()
        val toVisit = PriorityQueue<LocationWithCost>()

        (minSteps..maxSteps).forEach { i ->
            val heatLostRight = (1..i).fold(0) { acc, column -> acc + this[0][column] }
            toVisit.add(LocationWithCost(Location(0, i, RIGHT), heatLostRight))
            val heatLostDown = (1..i).fold(0) { acc, row -> acc + this[row][0] }
            toVisit.add(LocationWithCost(Location(i, 0, DOWN), heatLostDown))
        }

        while (toVisit.isNotEmpty()) {
            val current = toVisit.poll()
            if (current.row == endRow && current.column == endColumn) {
                return current.heatLost
            }

            if (current.heatLost >= (visited[current.location] ?: Int.MAX_VALUE)) {
                continue
            }
            visited[current.location] = current.heatLost

            when (current.direction) {
                UP, DOWN -> {
                    //LEFT
                    (minSteps..maxSteps)
                        .filter { (current.column - it) >= 0 }
                        .forEach { steps ->
                            val heatLost = (1..steps)
                                .fold(current.heatLost) { acc, i ->
                                    acc + this[current.row][current.column - i]
                                }
                            val location = LocationWithCost(
                                Location(current.row, current.column - steps, LEFT),
                                heatLost
                            )
                            if (!visited.contains(location.location)) {
                                toVisit.add(location)
                            }
                        }
                    //RIGHT
                    (minSteps..maxSteps)
                        .filter { (current.column + it) <= endColumn }
                        .forEach { steps ->
                            val heatLost = (1..steps)
                                .fold(current.heatLost) { acc, i ->
                                    acc + this[current.row][current.column + i]
                                }

                            val location = LocationWithCost(
                                Location(current.row, current.column + steps, RIGHT),
                                heatLost
                            )
                            if (!visited.contains(location.location)) {
                                toVisit.add(location)
                            }
                        }
                }

                LEFT, RIGHT -> {
                    //UP
                    (minSteps..maxSteps)
                        .filter { (current.row - it) >= 0 }
                        .forEach { steps ->
                            val heatLost = (1..steps)
                                .fold(current.heatLost) { acc, i ->
                                    acc + this[current.row - i][current.column]
                                }
                            val location = LocationWithCost(
                                Location(current.row - steps, current.column, UP),
                                heatLost
                            )
                            if (!visited.contains(location.location)) {
                                toVisit.add(location)
                            }
                        }
                    //DOWN
                    (minSteps..maxSteps)
                        .filter { (current.row + it) <= endRow }
                        .forEach { steps ->
                            val heatLost = (1..steps)
                                .fold(current.heatLost) { acc, i ->
                                    acc + this[current.row + i][current.column]
                                }

                            val location = LocationWithCost(
                                Location(current.row + steps, current.column, DOWN),
                                heatLost
                            )
                            if (!visited.contains(location.location)) {
                                toVisit.add(location)
                            }
                        }
                }
            }
        }

        return -1
    }

    private enum class Direction { UP, DOWN, LEFT, RIGHT }

    private data class Location(val row: Int, val column: Int, val direction: Direction)

    private data class LocationWithCost(val location: Location, val heatLost: Int = 0) : Comparable<LocationWithCost> {
        val row = location.row
        val column = location.column
        val direction = location.direction

        override fun compareTo(other: LocationWithCost): Int {
            return heatLost compareTo other.heatLost
        }
    }
}

