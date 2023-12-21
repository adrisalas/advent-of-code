package year2023

fun main() {
    val input = readInput("Day21")
    Day21.part1(input).println()
    Day21.part2(input).println()
}

object Day21 {
    fun part1(input: List<String>): Int {
        return input.gardenPlotsReached(64)
    }

    fun part2(input: List<String>): Long {
        println("f(x) = a*x^2 + b*x + c = 0")
        val x0 = 65
        val a0 = input.gardenPlotsReached(x0)
        println("f(0) = gardenPlotsReached(65 + 131*0) = gardenPlotsReached($x0) = $a0")
        val x1 = 65 + 131
        val a1 = input.gardenPlotsReached(x1)
        println("f(1) = gardenPlotsReached(65 + 131*1) = gardenPlotsReached($x1) = $a1")
        val x2 = 65 + (131 * 2)
        val a2 = input.gardenPlotsReached(x2)
        println("f(2) = gardenPlotsReached(65 + 131*2) = gardenPlotsReached($x2) = $a2")

        val steps = 26501365L
        val cycles = steps / 131
        val solution = ((a2 - a1) - (a1 - a0)) * (cycles * (cycles - 1) / 2) + (a1 - a0) * cycles + a0

        println("gardenPlotsReached($steps) = gardenPlotsReached(65 + 131*$cycles) = f($cycles)")
        println("f($cycles) = (($a2-$a1)-($a1-$a0))*$cycles*($cycles-1)/2) + ($a1-$a0)*$cycles  + $a0")
        println("f($cycles) = $solution")
        println("gardenPlotsReached($steps) = $solution")
        return solution
    }

    private data class Point(val row: Int, val column: Int) {
        fun neighbours(): Set<Point> {
            return setOf(
                Point(row - 1, column),
                Point(row + 1, column),
                Point(row, column - 1),
                Point(row, column + 1)
            )
        }
    }

    private fun List<String>.getStartingPoint(): Point {
        return this.flatMapIndexed { row, line ->
            line.mapIndexed { column, c ->
                if (c == 'S') Point(row, column) else null
            }.filterNotNull()
        }.first()
    }

    private fun List<String>.gardenPlotsReached(steps: Int): Int {
        val start = this.getStartingPoint()
        var reached = setOf(start)
        (1..steps)
        (1..steps).forEach { _ ->
            reached = reached.flatMap { pointReached ->
                pointReached.neighbours().filter { neighbour ->
                    val row = neighbour.row.mod(this.size)
                    val column = neighbour.column.mod(this[0].length)
                    this[row][column] != '#'
                }
            }.toSet()
        }

        return reached.size
    }
}

