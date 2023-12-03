package year2021

import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

private fun part1(input: List<Int>): Int {
    val median = input[((input.size / 2.0)).roundToInt()]
    return input.fold(0) { acc, value -> acc + abs(value - median) }
}

private fun calculateStep(from: Int, to: Int): Int {
    var counter = 0
    var i = abs(from - to)
    while (i > 0) {
        counter += i
        i--
    }
    return counter
}

private fun part2(input: List<Int>): Int {
    val average = input.fold(0) { acc, value -> acc + value } / input.size.toDouble()

    // There should be a mathematician formulae to know which one to choose before calculating them both...
    val average1 = floor(average).toInt()
    val average2 = ceil(average).toInt()

    val fuel1 = input.fold(0) { acc, value -> acc + calculateStep(value, average1) }
    val fuel2 = input.fold(0) { acc, value -> acc + calculateStep(value, average2) }

    return if (fuel1 < fuel2) fuel1 else fuel2
}

fun main() {
    val testInput = readInput("Day07_test")[0].split(",").map { it.toInt() }.sorted()
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")[0].split(",").map { it.toInt() }.sorted()
    println(part1(input))
    println(part2(input))
}
