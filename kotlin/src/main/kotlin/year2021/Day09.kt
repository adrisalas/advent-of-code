package year2021

fun main() {
    val input = readInput("Day09")
    Day09.part1(input).println()
    Day09.part2(input).println()
}


object Day09 {
    private const val MAX_VALUE = 9

    fun part1(input: List<String>): Int {
        val graph = input.mapIndexed { row, line ->
            line.mapIndexed { column, cell -> Point(row, column, cell.digitToInt()) }
        }

        return graph.flatMapIndexed { row, line ->
            line.filterIndexed { column, _ -> graph.isMinHeight(row, column) }
                .map { it.value + 1 }
        }.sum()
    }

    private fun List<List<Point>>.isMinHeight(row: Int, column: Int): Boolean {
        val maxRows = this.size
        val maxColumns = this[0].size

        val value = this[row][column].value

        if (value == MAX_VALUE) {
            return false
        }

        if (row > 0 && value > this[row - 1][column].value) {
            return false
        }

        if (row < (maxRows - 1) && value > this[row + 1][column].value) {
            return false
        }

        if (column > 0 && value > this[row][column - 1].value) {
            return false
        }

        if (column < (maxColumns - 1) && value > this[row][column + 1].value) {
            return false
        }

        return true
    }


    fun part2(input: List<String>): Int {
        val graph = input.mapIndexed { row, line ->
            line.mapIndexed { column, cell -> Point(row, column, cell.digitToInt()) }
        }

        graph.flatten().forEach { it.increaseBasinCountToLowestPoint(graph) }

        return graph
            .flatMapIndexed { row, line ->
                line.filterIndexed { column, _ -> graph.isMinHeight(row, column) }
                    .map { it.basinCount }
            }
            .sortedDescending()
            .take(3)
            .fold(1) { acc, it -> acc * it }
    }

    private data class Point(
        val row: Int,
        val column: Int,
        val value: Int,
        var basinCount: Int = 0,
    ) {

        fun increaseBasinCountToLowestPoint(graph: List<List<Point>>) {
            if (value == MAX_VALUE) {
                return
            }

            val maxRows = graph.size
            val maxColumns = graph[0].size

            if (row > 0 && value > graph[row - 1][column].value) {
                graph[row - 1][column].increaseBasinCountToLowestPoint(graph)
            } else if (row < (maxRows - 1) && value > graph[row + 1][column].value) {
                graph[row + 1][column].increaseBasinCountToLowestPoint(graph)
            } else if (column > 0 && value > graph[row][column - 1].value) {
                graph[row][column - 1].increaseBasinCountToLowestPoint(graph)
            } else if (column < (maxColumns - 1) && value > graph[row][column + 1].value) {
                graph[row][column + 1].increaseBasinCountToLowestPoint(graph)
            } else {
                basinCount++
            }
        }
    }
}

