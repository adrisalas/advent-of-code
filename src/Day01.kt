private fun part1(input: List<String>): Int {
    return input.sumOf { line ->
        val results = "[0-9]".toRegex().findAll(line)

        val firstDigit = results.first().value
        val lastDigit = results.last().value

        return@sumOf "$firstDigit$lastDigit".toInt()
    }
}


/**
 * If we just use toRegex().findAll(line) then the last() may not be correct
 * as we could have collision between some elements:
 *
 * Example: "twoeightwo"
 * - Expected output -> 22
 * - Using first() and last() -> 28. This is due to the collision with the t in: "eighTwo".
 *
 * This can be avoided finding the "first" match starting from the last part of the string.
 *
 * See: [Regex.lastMatch]
 */
private fun part2(input: List<String>): Int {
    return input.sumOf { line ->
        val regex = "[0-9]|one|two|three|four|five|six|seven|eight|nine".toRegex()

        val firstDigit = regex.firstMatch(line)?.toSingleDigit() ?: 0
        val lastDigit = regex.lastMatch(line)?.toSingleDigit() ?: 0

        return@sumOf "$firstDigit$lastDigit".toInt()
    }
}

private fun Regex.firstMatch(line: String): String? {
    return this.find(line)?.value
}

private fun Regex.lastMatch(line: String): String? {
    var lastMatch: String? = null
    var position = line.length
    while (lastMatch == null && position >= 0) {
        lastMatch = this.find(line, position)?.value
        position--
    }
    return lastMatch
}

fun String.toSingleDigit(): Int {
    return when (this) {
        "0", "zero" -> 0
        "1", "one" -> 1
        "2", "two" -> 2
        "3", "three" -> 3
        "4", "four" -> 4
        "5", "five" -> 5
        "6", "six" -> 6
        "7", "seven" -> 7
        "8", "eight" -> 8
        "9", "nine" -> 9
        else -> error("Invalid input")
    }
}

fun main() {
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
