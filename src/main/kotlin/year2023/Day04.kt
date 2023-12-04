package year2023

import kotlin.math.pow

fun main() {
    val input = readInput("Day04")
    Day04.part1(input).println()
    Day04.part2(input).println()
}

object Day04 {
    fun part1(input: List<String>): Int {

        return input
            .map { line ->
                val (_, numbers) = line.split(":")
                val (winningNumbers, myNumbers) = numbers.split("|")

                val setWinningNumbers = winningNumbers
                    .split(" ")
                    .filter { it != "" && it != " " }
                    .map { it.trim().toInt() }
                    .toSet()

                val howManyMatches = myNumbers
                    .split(" ")
                    .filter { it != "" && it != " " }
                    .map { it.trim().toInt() }
                    .count { setWinningNumbers.contains(it) }

                if (howManyMatches == 0) {
                    return@map 0.0
                } else {
                    return@map 2.0.pow(howManyMatches - 1)
                }

            }.sum()
            .toInt()
    }

    fun part2(input: List<String>): Long {
        val map = mutableMapOf<Int, Long>()

        for (i in 1..(input.size)) {
            map[i] = 1
        }

        for (line in input) {
            val (card, numbers) = line.split(":")
            val (winningNumbers, myNumbers) = numbers.split("|")
            val cardNumber = card.split("Card ")[1].trim().toInt()

            val setWinningNumbers = winningNumbers
                .split(" ")
                .filter { it != "" && it != " " }
                .map { it.trim().toInt() }
                .toSet()

            val howManyMatches = myNumbers
                .split(" ")
                .filter { it != "" && it != " " }
                .map { it.trim().toInt() }
                .count { setWinningNumbers.contains(it) }

            val howManyTimes = map[cardNumber]!!

            for (i in 1..howManyMatches) {
                val cardId = cardNumber + i
                if (cardId < (input.size + 1))
                    map[cardId] = map[cardId]!! + howManyTimes
            }
        }
        return map.values.sum()
    }
}

