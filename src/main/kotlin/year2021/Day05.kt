package year2021

private data class Point(val x: Int, val y: Int)
private data class Line(val pointA: Point, val pointB: Point)

private fun part1(input: List<String>): Int {
    val points: MutableMap<Point, Int> = HashMap()
    val lines = readLines(input)

    lines
        .map { toPointsPart1(it) }
        .flatten()
        .forEach { points[it] = points.getOrDefault(it, 0) + 1 }

    return points.values.count { it > 1 }
}

private fun readLines(input: List<String>): List<Line> {
    return input.map {
        val points = it.split(" -> ")
        val left = points[0].split(",")
        val begin = Point(
            Integer.valueOf(left[0]),
            Integer.valueOf(left[1])
        )
        val right = points[1].split(",")
        val end = Point(
            Integer.valueOf(right[0]),
            Integer.valueOf(right[1])
        )

        Line(begin, end)
    }
}

private fun toPointsPart1(line: Line): List<Point> {
    val (pointA, pointB) = line
    val points: MutableList<Point> = ArrayList()

    val begin = if (pointA.x < pointB.x || pointA.y < pointB.y) pointA else pointB
    val end = if (pointA == begin) pointB else pointA

    if (begin.x == end.x) {
        for (i in begin.y..end.y) {
            points.add(Point(begin.x, i))
        }
    } else if (begin.y == end.y) {
        for (i in begin.x..end.x) {
            points.add(Point(i, begin.y))
        }
    }

    return points
}


private fun part2(input: List<String>): Int {
    val points: MutableMap<Point, Int> = HashMap()
    val lines = readLines(input)

    lines
        .map { toPointsPart2(it) }
        .flatten()
        .forEach { points[it] = points.getOrDefault(it, 0) + 1 }

    return points.values.count { it > 1 }
}

private fun toPointsPart2(line: Line): List<Point> {
    val (pointA, pointB) = line
    val points: MutableList<Point> = ArrayList()

    var begin = if (pointA.x < pointB.x || pointA.y < pointB.y) pointA else pointB
    var end = if (pointA == begin) pointB else pointA

    if (begin.x == end.x) {
        for (i in begin.y..end.y) {
            points.add(Point(begin.x, i))
        }
    } else if (begin.y == end.y) {
        for (i in begin.x..end.x) {
            points.add(Point(i, begin.y))
        }
    } else {
        begin = if (pointA.x < pointB.x) pointA else pointB
        end = if (pointA == begin) pointB else pointA
        if (needsToGoUp(begin, end)) {
            for (i in 0..(end.x - begin.x)) {
                points.add(Point(begin.x + i, begin.y - i))
            }
        } else {
            for (i in 0..(end.x - begin.x)) {
                points.add(Point(begin.x + i, begin.y + i))
            }
        }
    }

    return points
}

private fun needsToGoUp(begin: Point, end: Point): Boolean {
    return begin.y > end.y
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)
    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
