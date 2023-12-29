package year2023

import java.util.*

fun main() {
    val input = readInput("Day10")
    Day10.part1(input).println()
    Day10.part2(input).println()
}

object Day10 {
    data class Pipe(val row: Int, val column: Int, val value: Char, val previousPipe: Pipe? = null) {
        fun getDistanceToStart(): Int {
            if (previousPipe == null) {
                return 0
            }
            return 1 + previousPipe.getDistanceToStart()
        }
    }

    fun part1(input: List<String>): Int {
        val start = input.findStartingPosition()
        val visited = mutableListOf(start)
        val toVisit = input.findPipesConnectedToStart(start).toMutableList()

        while (toVisit.isNotEmpty()) {
            val node = toVisit.removeAt(0)

            visited.add(node)

            val newNode = input.findPipeConnectedTo(node)
            if (newNode != null
                && !visited.containsPipeWithPosition(newNode.row, newNode.column)
                && !toVisit.containsPipeWithPosition(newNode.row, newNode.column)
            ) {
                toVisit.add(newNode)
            }

        }

        return visited.maxOf { it.getDistanceToStart() }
    }

    private fun List<String>.findStartingPosition(): Pipe {
        this.forEachIndexed start@{ row, line ->
            line.forEachIndexed { column, value ->
                if (value == 'S') {
                    return Pipe(row, column, value)
                }
            }
        }
        error("Not found")
    }

    private fun List<String>.findPipesConnectedToStart(start: Pipe): List<Pipe> {
        val (row, column) = start
        val values = mutableListOf<Pipe>()

        val charAtNorth = this.getElementAt(row - 1, column)
        if (charAtNorth.isLookingSouth()) {
            values.add(Pipe(row - 1, column, charAtNorth, start))
        }

        val charAtSouth = this.getElementAt(row + 1, column)
        if (charAtSouth.isLookingNorth()) {
            values.add(Pipe(row + 1, column, charAtSouth, start))
        }

        val charAtWest = this.getElementAt(row, column - 1)
        if (charAtWest.isLookingEast()) {
            values.add(Pipe(row, column - 1, charAtWest, start))
        }

        val charAtEast = this.getElementAt(row, column + 1)
        if (charAtEast.isLookingWest()) {
            values.add(Pipe(row, column + 1, charAtEast, start))
        }

        return values
    }

    private fun List<String>.findPipeConnectedTo(pipe: Pipe): Pipe? {
        if (pipe.previousPipe == null) {
            error("This method does not allow the starting pipe")
        }

        val (rowFrom, columnFrom) = pipe.previousPipe

        val row = when (pipe.value) {
            '|' -> if (rowFrom == pipe.row - 1) pipe.row + 1 else pipe.row - 1
            'L', 'J' -> if (rowFrom == pipe.row) pipe.row - 1 else pipe.row
            'F', '7' -> if (rowFrom == pipe.row) pipe.row + 1 else pipe.row
            else -> pipe.row
        }

        val column = when (pipe.value) {
            '-' -> if (columnFrom == pipe.column - 1) pipe.column + 1 else pipe.column - 1
            'J', '7' -> if (columnFrom == pipe.column) pipe.column - 1 else pipe.column
            'L', 'F' -> if (columnFrom == pipe.column) pipe.column + 1 else pipe.column
            else -> pipe.column
        }

        val value = this.getElementAt(row, column)
        if (value == '.') {
            return null
        }
        return Pipe(row, column, value, pipe)
    }

    private fun Char.isLookingSouth(): Boolean {
        return this == '|' || this == '7' || this == 'F'
    }

    private fun Char.isLookingNorth(): Boolean {
        return this == '|' || this == 'L' || this == 'J'
    }

    private fun Char.isLookingWest(): Boolean {
        return this == '-' || this == 'J' || this == '7'
    }

    private fun Char.isLookingEast(): Boolean {
        return this == '-' || this == 'L' || this == 'F'
    }

    private fun List<String>.getElementAt(row: Int, column: Int): Char {
        if (row < 0 || row >= this.size || column < 0 || column >= this[0].length) {
            return '.'
        }
        return this[row][column]
    }

    private fun List<Pipe>.containsPipeWithPosition(row: Int, column: Int): Boolean {
        return this.any { it.row == row && it.column == column }
    }


    fun part2(input: List<String>): Int {
        val start = input.findStartingPosition()
        val visited = mutableListOf(start)
        val toVisit = input.findPipesConnectedToStart(start).toMutableList()

        while (toVisit.isNotEmpty()) {
            val node = toVisit.removeAt(0)

            visited.add(node)

            val newNode = input.findPipeConnectedTo(node)
            if (newNode != null
                && !visited.containsPipeWithPosition(newNode.row, newNode.column)
                && !toVisit.containsPipeWithPosition(newNode.row, newNode.column)
            ) {
                toVisit.add(newNode)
            }

        }

        val realSymbolOfStart = input.getCharOfStart(start)
        /*
         * If we cross the "rope" we will cross through outside/inside:
         * . | .     <--- One point will be outside, the other one inside
         * . F - 7 . <--- Both points are outside
         * . F - J . <--- One point will be outside, the other one inside
         * . L - J . <--- Both points are outside
         * . L - 7 . <--- One point will be outside, the other one inside
         */
        return input
            .map { it.replace('S', realSymbolOfStart) }
            .mapIndexed { row, line ->
                val stack: Stack<Char> = Stack()
                var insideLoop = false
                var count = 0

                line.forEachIndexed { column, value ->
                    val isLoopPipe = visited.containsPipeWithPosition(row, column)
                    when {
                        isLoopPipe && value == '|' -> {
                            insideLoop = !insideLoop
                        }

                        isLoopPipe && (value == 'F' || value == 'L') -> {
                            stack.push(value)
                        }

                        isLoopPipe && value == '7' -> {
                            val prev = stack.pop()
                            if (prev == 'L') {
                                insideLoop = !insideLoop
                            }
                        }

                        isLoopPipe && value == 'J' -> {
                            val prev = stack.pop()
                            if (prev == 'F') {
                                insideLoop = !insideLoop
                            }
                        }
                    }
                    if (isLoopPipe) {
                        return@forEachIndexed
                    }
                    if (insideLoop) {
                        count++
                    }
                }

                count
            }.sum()
    }

    private fun List<String>.getCharOfStart(start: Pipe): Char {
        val (row, column) = start

        val charAtNorth = this.getElementAt(row - 1, column)
        val north = charAtNorth.isLookingSouth()

        val charAtSouth = this.getElementAt(row + 1, column)
        val south = charAtSouth.isLookingNorth()

        val charAtWest = this.getElementAt(row, column - 1)
        val west = charAtWest.isLookingEast()

        val charAtEast = this.getElementAt(row, column + 1)
        val east = charAtEast.isLookingWest()

        return when {
            north && south -> '|'
            north && east -> 'L'
            north && west -> 'J'
            south && west -> '7'
            south && east -> 'F'
            else -> '-'
        }
    }

}

