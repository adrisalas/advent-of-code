package year2023

fun main() {
    val input = readInput("Day18")
    Day18.part1(input).println()
    Day18.part2(input).println()
}

object Day18 {
    fun part1(input: List<String>): Long {
        val holes = input.map { getHole(it) }
        return holes.cubicMetersOfLava()
    }

    fun part2(input: List<String>): Long {
        val holes = input.map { getHole(it, true) }
        return holes.cubicMetersOfLava()
    }

    private data class Direction(val row: Long, val column: Long)

    private data class Hole(val direction: Direction, val meters: Long)

    private val colorMap = mapOf(
        'U' to Direction(-1, 0),
        '3' to Direction(-1, 0),
        'D' to Direction(1, 0),
        '1' to Direction(1, 0),
        'L' to Direction(0, -1),
        '2' to Direction(0, -1),
        'R' to Direction(0, 1),
        '0' to Direction(0, 1)
    )

    private fun getHole(line: String, useColor: Boolean = false): Hole {
        val (direction, meters, color) = line.split(" ")

        return if (useColor) {
            Hole(colorMap.getValue(color[7]), color.substring(2..6).toLong(16))
        } else {
            Hole(colorMap.getValue(direction[0]), meters.toLong())
        }
    }


    private fun List<Hole>.cubicMetersOfLava(): Long {
        val firstHole = 1
        val borderArea = this.borderArea()
        val interiorArea = this.shoelaceFormula()

        return firstHole + borderArea + interiorArea
    }

    private fun List<Hole>.borderArea(): Long {
        return this.sumOf { it.meters }
    }

    private fun List<Hole>.shoelaceFormula(): Long {
        val vertices = mutableListOf(Direction(0, 0))

        for (hole in this) {
            vertices.add(
                Direction(
                    row = hole.direction.row * hole.meters + vertices.last().row,
                    column = hole.direction.column * hole.meters + vertices.last().column
                )
            )
        }
        
        var shoelaceSum = 0L

        for (i in vertices.indices) {
            val point1 = vertices[i]
            val point2 = vertices[(i + 1) % vertices.size]
            shoelaceSum += point1.column * point2.row - point1.row * point2.column
        }

        return (shoelaceSum / 2) - (this.borderArea() / 2)
    }
}

