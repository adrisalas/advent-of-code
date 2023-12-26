package year2023

fun main() {
    val input = readInput("Day23")
    Day23.part1(input).println()
    Day23.part2(input).println()
}

object Day23 {
    fun part1(trails: List<String>): Int {
        val visited = List(trails.size) {
            MutableList(trails[0].length) {
                false
            }
        }

        return trails.longestHike(
            from = Point(0, 1),
            target = Point(trails.size - 1, trails[0].length - 2),
            visited = visited,
            pathGenerator = pathGeneratorPart1(trails)
        )
    }

    fun part2(trails: List<String>): Int {
        val visited = List(trails.size) {
            MutableList(trails[0].length) {
                false
            }
        }
        val from = Point(0, 1)
        val target = Point(trails.size - 1, trails[0].length - 2)
        return trails.longestHike(
            from = Point(0, 1),
            target = Point(trails.size - 1, trails[0].length - 2),
            visited = visited,
            pathGenerator = pathGeneratorPart2(trails, from, target)
        )
    }

    private data class Point(val row: Int, val column: Int)

    private fun List<String>.longestHike(
        from: Point,
        target: Point,
        distance: Int = 0,
        visited: List<MutableList<Boolean>>,
        pathGenerator: (Point) -> List<Pair<Point, Int>>
    ): Int {
        if (from == target) {
            return distance
        }

        visited[from.row][from.column] = true
        val max = pathGenerator(from)
            .filter { (path, _) -> !visited[path.row][path.column] }
            .maxOfOrNull { (neighbour, weight) ->
                this.longestHike(
                    from = neighbour,
                    target = target,
                    visited = visited,
                    distance = distance + weight,
                    pathGenerator = pathGenerator
                )
            }
        visited[from.row][from.column] = false

        return max ?: 0
    }

    private fun pathGeneratorPart1(trails: List<String>): (Point) -> List<Pair<Point, Int>> {
        return { point ->
            val (row, column) = point
            when (trails[row][column]) {
                '>' -> listOf(Point(row, column + 1) to 1)
                '<' -> listOf(Point(row, column - 1) to 1)
                'v' -> listOf(Point(row + 1, column) to 1)
                '^' -> listOf(Point(row - 1, column) to 1)
                else -> {
                    trails.validPathsFrom(point).map { it to 1 }
                }
            }
        }

    }

    private fun List<String>.validPathsFrom(point: Point): List<Point> {
        val trails = this
        val (row, column) = point
        val paths = mutableListOf<Point>()

        val canGoUp = (row - 1 >= 0) && trails[row - 1][column] != '#'
        if (canGoUp) paths.add(Point(row - 1, column))

        val canGoDown = (row + 1 < trails.size) && trails[row + 1][column] != '#'
        if (canGoDown) paths.add(Point(row + 1, column))

        val canGoLeft = (column - 1 >= 0) && trails[row][column - 1] != '#'
        if (canGoLeft) paths.add(Point(row, column - 1))

        val canGoRight = (column + 1 < trails[0].length) && trails[row][column + 1] != '#'
        if (canGoRight) paths.add(Point(row, column + 1))

        return paths
    }

    private fun pathGeneratorPart2(
        trails: List<String>,
        from: Point,
        target: Point
    ): (Point) -> List<Pair<Point, Int>> {
        val junctions = mutableMapOf(
            from to mutableListOf<Pair<Point, Int>>(),
            target to mutableListOf(),
        )

        for (row in trails.indices) {
            for (column in trails[row].indices) {
                if (trails[row][column] == '.') {
                    val point = Point(row, column)
                    if (trails.validPathsFrom(point).size > 2) {
                        junctions[point] = mutableListOf()
                    }
                }
            }
        }

        for (junctionEdge in junctions.keys) {
            var edges = setOf(junctionEdge)
            val visited = mutableSetOf(junctionEdge)
            var distance = 0

            while (edges.isNotEmpty()) {
                distance++
                edges = buildSet {
                    for (point in edges) {
                        trails.validPathsFrom(point)
                            .filter { it !in visited }
                            .forEach {
                                if (it in junctions) {
                                    val next = junctions.getValue(junctionEdge)
                                    next.add(it to distance)
                                } else {
                                    add(it)
                                    visited.add(it)
                                }
                            }
                    }
                }
            }
        }

        return { point -> junctions.getValue(point) }

    }
}

