package year2023

fun main() {
    val input = readInput("Day02")
    Day02.part1(input).println()
    Day02.part2(input).println()
}

object Day02 {
    private val gameIdPattern = "(Game) (\\d+)".toRegex().toPattern()
    private val greenPattern = "(\\d+) (green)".toRegex().toPattern()
    private val bluePattern = "(\\d+) (blue)".toRegex().toPattern()
    private val redPattern = "(\\d+) (red)".toRegex().toPattern()

    fun part1(input: List<String>): Int {
        return input
            .filter { game ->
                return@filter game
                    .split(";")
                    .all { isValid(it) }
            }
            .sumOf { extractGameId(it) }
    }

    fun part2(input: List<String>): Int {
        return input
            .sumOf { game -> calculatePower(game) }
    }

    private fun isValid(
        it: String,
        maxBlue: Int = 14,
        maxGreen: Int = 13,
        maxRed: Int = 12
    ): Boolean {
        val blueMatcher = bluePattern.matcher(it)
        val redMatcher = redPattern.matcher(it)
        val greenMatcher = greenPattern.matcher(it)

        val blue = if (blueMatcher.find()) blueMatcher.group(1).toInt() else 0
        val red = if (redMatcher.find()) redMatcher.group(1).toInt() else 0
        val green = if (greenMatcher.find()) greenMatcher.group(1).toInt() else 0

        return blue <= maxBlue
                && green <= maxGreen
                && red <= maxRed
    }

    private fun extractGameId(set: String): Int {
        val gameIdMatcher = gameIdPattern.matcher(set)
        return when {
            !gameIdMatcher.find() -> 0
            else -> gameIdMatcher.group(2).toInt()
        }
    }

    private fun calculatePower(game: String): Int {
        var maxBlue = 0
        var maxRed = 0
        var maxGreen = 0
        for (set in game.split(";")) {
            val blueMatcher = bluePattern.matcher(set)
            if (blueMatcher.find()) {
                val blue = blueMatcher.group(1).toInt()
                if (blue > maxBlue) {
                    maxBlue = blue
                }
            }
            val redMatcher = redPattern.matcher(set)
            if (redMatcher.find()) {
                val red = redMatcher.group(1).toInt()
                if (red > maxRed) {
                    maxRed = red
                }
            }
            val greenMatcher = greenPattern.matcher(set)
            if (greenMatcher.find()) {
                val green = greenMatcher.group(1).toInt()
                if (greenMatcher.group(1).toInt() > maxGreen) {
                    maxGreen = green
                }
            }
        }
        return maxGreen * maxRed * maxBlue
    }


}
