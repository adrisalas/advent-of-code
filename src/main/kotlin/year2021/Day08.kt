package year2021

private val SEGMENTS_TO_DIGITS = mapOf(
    setOf('a', 'b', 'c', 'e', 'f', 'g') to 0,
    setOf('c', 'f') to 1,
    setOf('a', 'c', 'd', 'e', 'g') to 2,
    setOf('a', 'c', 'd', 'f', 'g') to 3,
    setOf('b', 'c', 'd', 'f') to 4,
    setOf('a', 'b', 'd', 'f', 'g') to 5,
    setOf('a', 'b', 'd', 'e', 'f', 'g') to 6,
    setOf('a', 'c', 'f') to 7,
    setOf('a', 'b', 'c', 'd', 'e', 'f', 'g') to 8,
    setOf('a', 'b', 'c', 'd', 'f', 'g') to 9
)

private fun part1(input: List<String>): Int {
    val outputs = input.map { line ->
        line.split(" | ").last()
    }
    val digits = outputs.flatMap { it.split(" ") }

    // 1 -> 2 segment
    // 4 -> 4 segment
    // 7 -> 3 segment
    // 8 -> 7 segment
    return digits.count { it.length == 2 || it.length == 4 || it.length == 3 || it.length == 7 }
}

private fun part2(input: List<String>): Int {
    val lines = input.map {
        val line = it.split(" | ")
        val signal = line.first()
        val output = line.last()

        Pair(first = signal.split(" "), second = output.split(" "))
    }
    return lines.sumOf { (signal, output) ->
        translateConfiguration(signal, output)
    }
}

private fun translateConfiguration(signals: List<String>, output: List<String>): Int {
    val cables = setOf('a', 'b', 'c', 'd', 'e', 'f', 'g')

    val decoder = getDecoder(cables, signals)

    return output
        .joinToString("") { encodeCables ->
            val decodedCables = encodeCables.map { decoder[it]!! }.toSet()
            val digit = SEGMENTS_TO_DIGITS[decodedCables]!!
            "$digit"
        }
        .toInt()
}

private fun getDecoder(
    cables: Set<Char>,
    signals: List<String>
): Map<Char, Char> {
    var found = false
    var randomPermutation = mapOf<Char, Char>()
    while (!found) {
        randomPermutation = getRandomPermutation(cables)

        found = signals.all { signal ->
            val originalCables = signal.map { randomPermutation[it]!! }.toSet()
            SEGMENTS_TO_DIGITS.containsKey(originalCables)
        }
    }
    return randomPermutation
}

//TODO This is inefficient as we are trusting in randomness
private fun getRandomPermutation(cables: Set<Char>): Map<Char, Char> {
    return cables
        .zip(cables.shuffled())
        .toMap()
}

fun main() {
    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
