package year2023

private val bluePattern = "(\\d+) (blue)".toRegex().toPattern()
private val redPattern = "(\\d+) (red)".toRegex().toPattern()
private val greenPattern = "(\\d+) (green)".toRegex().toPattern()
private val gameIdPattern = "(Game) (\\d+)".toRegex().toPattern()

private fun part1(input: List<String>): Int {
    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14
    val maxCubes = maxRed + maxGreen + maxBlue

    return input
        .filter { game ->
            return@filter game
                .split(";")
                .all {
                    val blueMatcher = bluePattern.matcher(it)
                    val redMatcher = redPattern.matcher(it)
                    val greenMatcher = greenPattern.matcher(it)

                    val blue = if (blueMatcher.find()) blueMatcher.group(1).toInt() else 0
                    val red = if (redMatcher.find()) redMatcher.group(1).toInt() else 0
                    val green = if (greenMatcher.find()) greenMatcher.group(1).toInt() else 0
                    return@all blue <= maxBlue
                            && green <= maxGreen
                            && red <= maxRed
                            && blue + green + red <= maxCubes
                }
        }
        .sumOf {
            val gameIdMatcher = gameIdPattern.matcher(it)
            return@sumOf if (gameIdMatcher.find()) gameIdMatcher.group(2).toInt() else 0
        }
}

private fun part2(input: List<String>): Int {
    return input
        .sumOf { game ->
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
            return@sumOf maxGreen * maxRed * maxBlue
        }
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
