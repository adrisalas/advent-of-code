package year2023

private fun part1(input: List<String>): Int {
    return input.size
}

private fun part2(input: List<String>): Int {
    return input.size
}

fun main() {
    val testInput = readInput("Day00_test")
    check(part1(testInput) == 1)

    val input = readInput("Day00")
    part1(input).println()
    part2(input).println()
}