package year2023

import kotlin.math.abs

fun main() {
    val input = readInput("Day11")
    Day11.part1(input).println()
    Day11.part2(input).println()
}

object Day11 {
    fun part1(input: List<String>): Long {
        val expandedGalaxy = expandGalaxy(input)
        val galaxies = findGalaxies(expandedGalaxy)

        val distances = mutableListOf<Long>()

        for (i in galaxies.indices) {
            for (j in i + 1 until galaxies.size) {
                distances.add(galaxies[i].distanceTo(galaxies[j]))
            }
        }
        return distances.sum()
    }

    data class Galaxy(val row: Long, val column: Long) {
        fun distanceTo(other: Galaxy): Long {
            return abs(other.row - this.row) + abs(other.column - this.column)
        }
    }

    private fun findGalaxies(expandedGalaxy: List<String>): List<Galaxy> {
        val galaxies = mutableListOf<Galaxy>()

        var millionsOfRows = 0L
        expandedGalaxy.forEachIndexed { row, line ->
            if (expandedGalaxy[row].all { it == ' ' }) {
                millionsOfRows++
            }
            var millionsOfColumns = 0L
            line.forEachIndexed { column, value ->
                if (expandedGalaxy.all { it[column] == ' ' }) {
                    millionsOfColumns++
                }
                if (value == '#') {
                    galaxies.add(
                        Galaxy(
                            row.toLong() + (millionsOfRows * 999_999L),
                            column.toLong() + (millionsOfColumns * 999_999L)
                        )
                    )
                }
            }
        }
        return galaxies
    }

    fun expandGalaxy(input: List<String>): List<String> {
        val expandedGalaxy = mutableListOf<String>()

        for (row in input) {
            expandedGalaxy.add(row)
            val emptyRow = row.all { it == '.' }
            if (emptyRow) {
                expandedGalaxy.add(row)
            }
        }

        for (column in (expandedGalaxy[0].length - 1) downTo 0) {
            val emptyColumn = expandedGalaxy.all { it[column] == '.' }
            if (!emptyColumn) {
                continue
            }
            for (row in 0 until expandedGalaxy.size) {
                val sb = StringBuilder(expandedGalaxy[row])
                sb.insert(column, '.')
                expandedGalaxy[row] = sb.toString()
            }
        }
        return expandedGalaxy
    }

    fun part2(input: List<String>): Long {
        val expandedGalaxy = expandGalaxyLikeReallyReallyBig(input)
        val galaxies = findGalaxies(expandedGalaxy)

        val distances = mutableListOf<Long>()

        for (i in galaxies.indices) {
            for (j in i + 1 until galaxies.size) {
                distances.add(galaxies[i].distanceTo(galaxies[j]))
            }
        }
        return distances.sum()
    }

    // '.' <-- One distance
    // ' ' <-- One million distance
    fun expandGalaxyLikeReallyReallyBig(input: List<String>): List<String> {
        val expandedGalaxy = mutableListOf<String>()

        for (row in input) {
            val emptyRow = row.all { it == '.' }
            if (emptyRow) {
                expandedGalaxy.add(row.replace(".", " "))
            } else {
                expandedGalaxy.add(row)
            }
        }

        for (column in (expandedGalaxy[0].length - 1) downTo 0) {
            val emptyColumn = expandedGalaxy.all { it[column] == '.' || it[column] == ' ' }
            if (!emptyColumn) {
                continue
            }
            for (row in 0 until expandedGalaxy.size) {
                val sb = StringBuilder(expandedGalaxy[row])
                sb.replace(column, column + 1, " ")
                expandedGalaxy[row] = sb.toString()
            }
        }
        return expandedGalaxy
    }
}

