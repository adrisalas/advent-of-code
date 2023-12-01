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
 * See: [getLastMatch]
 */
private fun part2(input: List<String>): Int {
    return input.sumOf { line ->
        val regex = "[0-9]|one|two|three|four|five|six|seven|eight|nine".toRegex()

        val firstDigit = getFirstMatch(regex, line)
        val lastDigit = getLastMatch(regex, line)

        return@sumOf "$firstDigit$lastDigit".toInt()
    }
}

private fun getFirstMatch(regex: Regex, line: String): Char {
    val number = regex.find(line)?.value
    return number?.fromNumberWordToDigit() ?: '0'
}

private fun getLastMatch(regex: Regex, line: String): Char {
    var number: String? = null
    var position = line.length
    while (number == null && position >= 0) {
        number = regex.find(line, position)?.value
        position--
    }
    return number?.fromNumberWordToDigit() ?: '0'
}

fun String.fromNumberWordToDigit(): Char {
    return when (this) {
        "0", "zero" -> '0'
        "1", "one" -> '1'
        "2", "two" -> '2'
        "3", "three" -> '3'
        "4", "four" -> '4'
        "5", "five" -> '5'
        "6", "six" -> '6'
        "7", "seven" -> '7'
        "8", "eight" -> '8'
        "9", "nine" -> '9'
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
