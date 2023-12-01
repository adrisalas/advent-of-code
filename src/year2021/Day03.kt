package year2021

private const val ONE = '1'
private const val ZERO = '0'

private fun part1(input: List<String>): Int {

    val mostCommonBit = findMostCommonBit(input)
    val gammaRate = Integer.valueOf(mostCommonBit, 2)
    val leastCommonBit = reverse(mostCommonBit)
    val epsilonRate = Integer.valueOf(leastCommonBit, 2)

    return gammaRate * epsilonRate
}

private fun findMostCommonBit(input: List<String>): String {
    if (input.isEmpty()) {
        throw RuntimeException("Input shouldn't be empty")
    }

    val mostCommonBits = Array(input[0].length) { 0 }

    for (number in input) {
        number.forEachIndexed { index, bit ->
            when (bit) {
                ONE -> mostCommonBits[index]++
                else -> mostCommonBits[index]--
            }
        }
    }

    return mostCommonBits
        .map { if (it > 0) ONE else ZERO }
        .joinToString(separator = "")
}

private fun reverse(input: String): String {
    return input
        .toCharArray()
        .map { if (it == ONE) ZERO else ONE }
        .joinToString(separator = "")
}

private fun part2(input: List<String>): Int {
    val oxygenGeneratorRating = oxygenGeneratorRating(input)
    val co2ScrubberRating = co2ScrubberRating(input)

    return oxygenGeneratorRating * co2ScrubberRating
}

private fun oxygenGeneratorRating(input: List<String>): Int {
    return ratingCalculator(input, ::getMostCommonBitForPosition)
}


private fun co2ScrubberRating(input: List<String>): Int {
    return ratingCalculator(input, ::getLeastCommonBitForPosition)
}

private fun ratingCalculator(input: List<String>, extractBit: (List<String>, Int) -> Char): Int {
    var rating: Int? = null

    var position = 0
    var data = input.toList()

    while (rating == null && position < input.size) {
        val bit = extractBit(data, position)
        data = extractDataByBitInPosition(data, bit, position)
        position++
        if (data.size == 1) {
            rating = Integer.valueOf(data[0], 2)
        }
    }
    return rating!!
}

private fun getMostCommonBitForPosition(input: List<String>, position: Int): Char {
    val counter = input.fold(0) { sum, number ->
        if (number[position] == ONE) sum + 1 else sum - 1
    }
    return if (counter >= 0) ONE else ZERO
}

private fun getLeastCommonBitForPosition(input: List<String>, position: Int): Char {
    val mostCommonBit = getMostCommonBitForPosition(input, position)
    return if (mostCommonBit == ZERO) ONE else ZERO
}

private fun extractDataByBitInPosition(data: List<String>, mostCommonBit: Char, position: Int): List<String> {
    return data.filter { it[position] == mostCommonBit }.toList()
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
