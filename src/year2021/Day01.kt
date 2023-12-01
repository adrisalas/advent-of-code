package year2021

private fun part1(input: List<String>): Int {
    val numbers = input.map { it.toInt() }
    var count = 0
    var previous = numbers[0]

    for (number in numbers) {
        if (number > previous) {
            count++
        }
        previous = number
    }
    return count
}

private fun part2(input: List<String>): Int {
    val numbers = input.map { it.toInt() }
    val window = mutableListOf(numbers[0], numbers[1], numbers[3])
    var previous = window.reduce { acc, i -> i + acc }
    var count = 0

    for (i in 3 until numbers.size) {
        window.removeAt(0)
        window.add(numbers[i])
        val value = window.reduce { acc, n -> n + acc }
        if (value > previous) {
            count++
        }
        previous = value
    }
    return count
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
