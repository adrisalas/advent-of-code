package year2023

import year2023.Day14.Direction.*

fun main() {
    val input = readInput("Day14")
    Day14.part1(input).println()
    Day14.part2(input).println()
}

object Day14 {
    fun part1(input: List<String>): Int {
        return input
            .rollRocksToNorth()
            .mapIndexed { row, line -> calculateLoad(line, row, input.size) }
            .sum()
    }

    fun part2(input: List<String>): Int {
        var rocks = input

        val beforeCycle = mutableListOf<Int>()
        beforeCycle.add(rocks.rocksCustomHash())

        val cycleMemo = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until 1_000_000_000L) {
            rocks = cycle(rocks)
            
            val hash = rocks.rocksCustomHash()
            if (!beforeCycle.contains(hash)) {
                beforeCycle.add(hash)
            } else if (!cycleMemo.any { it.first == hash }) {
                cycleMemo.add(Pair(hash, rocks.mapIndexed { row, line -> calculateLoad(line, row, input.size) }.sum()))
            } else {
                break
            }
        }

        return cycleMemo[((1_000_000_000L - beforeCycle.size) % cycleMemo.size).toInt()].second
    }

    private fun List<String>.rocksCustomHash(): Int {
        val rowLength = this[0].length

        return this
            .mapIndexed { row, line ->
                line.mapIndexed { column, char ->
                    if (char == 'O') ((column + 1) + (rowLength * row)) else 0
                }
            }
            .flatten()
            .sum()
    }

    private val memo = mutableMapOf<Int, List<String>>()
    private var count = 0
    private fun cycle(rocks: List<String>): List<String> {
        val inputHash = rocks.rocksCustomHash()
        println("${count}: $inputHash")
        count++

        return memo.getOrPut(inputHash) {
            rocks.rollRocksToNorth()
                .rollRocksToWest()
                .rollRocksToSouth()
                .rollRocksToEast()
        }
    }

    private fun calculateLoad(line: String, row: Int, numberOfRows: Int): Int {
        val multiplier = (numberOfRows - row)
        val count = line.count { it == 'O' }

        return multiplier * count
    }

    private enum class Direction { NORTH, SOUTH, WEST, EAST }

    private fun List<String>.rollRocksToNorth(): List<String> {
        val builder = mutableListOf<StringBuilder>()

        this.forEach {
            builder.add(StringBuilder(it))
        }

        for (column in 0 until builder[0].length) {
            while (!areRocksVerticalSorted(builder, column, NORTH)) {
                applyVerticalBubbleSortOnce(builder, column, NORTH)
            }
        }

        return builder.map { it.toString() }
    }

    private fun List<String>.rollRocksToSouth(): List<String> {
        val builder = mutableListOf<StringBuilder>()

        this.forEach {
            builder.add(StringBuilder(it))
        }

        for (column in 0 until builder[0].length) {
            while (!areRocksVerticalSorted(builder, column, SOUTH)) {
                applyVerticalBubbleSortOnce(builder, column, SOUTH)
            }
        }

        return builder.map { it.toString() }
    }


    private fun applyVerticalBubbleSortOnce(builder: MutableList<StringBuilder>, column: Int, direction: Direction) {
        assert(direction == NORTH || direction == SOUTH)

        val first = if (direction == NORTH) 0 else 1
        val second = if (first == 0) 1 else 0

        for (row in 0 until (builder.size - 1)) {
            if (builder[row + first][column] == '.' && builder[row + second][column] == 'O') {
                builder[row + first][column] = 'O'
                builder[row + second][column] = '.'
            }
        }
    }

    private fun areRocksVerticalSorted(
        builder: MutableList<StringBuilder>,
        column: Int,
        direction: Direction
    ): Boolean {
        assert(direction == NORTH || direction == SOUTH)
        val it = if (direction == NORTH) builder else builder.reversed()

        return it
            .map { it[column] }
            .zipWithNext()
            .all { !(it.first == '.' && it.second == 'O') }
    }

    private fun List<String>.rollRocksToWest(): List<String> {
        val builder = mutableListOf<StringBuilder>()

        this.forEach {
            builder.add(StringBuilder(it))
        }

        for (row in 0 until builder.size) {
            while (!areRocksHorizontalSorted(builder[row], WEST)) {
                applyHorizontalBubbleSortOnce(builder[row], WEST)
            }
        }

        return builder.map { it.toString() }
    }

    private fun List<String>.rollRocksToEast(): List<String> {
        val builder = mutableListOf<StringBuilder>()

        this.forEach {
            builder.add(StringBuilder(it))
        }

        for (row in 0 until builder.size) {
            while (!areRocksHorizontalSorted(builder[row], EAST)) {
                applyHorizontalBubbleSortOnce(builder[row], EAST)
            }
        }

        return builder.map { it.toString() }
    }

    private fun areRocksHorizontalSorted(builder: StringBuilder, direction: Direction): Boolean {
        assert(direction == WEST || direction == EAST)

        val it = if (direction == WEST) builder.toString() else builder.reversed()

        return it
            .zipWithNext()
            .all { !(it.first == '.' && it.second == 'O') }
    }

    private fun applyHorizontalBubbleSortOnce(builder: StringBuilder, direction: Direction) {
        assert(direction == WEST || direction == EAST)

        val first = if (direction == WEST) 0 else 1
        val second = if (first == 0) 1 else 0

        for (column in 0 until (builder.length - 1)) {
            if (builder[column + first] == '.' && builder[column + second] == 'O') {
                builder[column + first] = 'O'
                builder[column + second] = '.'
            }
        }
    }
}

