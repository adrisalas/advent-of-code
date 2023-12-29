package year2023

fun main() {
    val input = readInput("Day12")
    Day12.part1(input).println()
    Day12.part2(input).println()
}

object Day12 {
    private val memo = hashMapOf<Pair<String, List<Int>>, Long>()

    fun part1(input: List<String>): Long {
        return input.map { line ->
            val springs = line.split(" ")[0]
            val numbers = line.split(" ")[1]
                .split(",")
                .filter { it.isNotBlank() }
                .map { it.toInt() }

            Pair(springs, numbers)
        }.sumOf { countSolutions(it.first, it.second) }
    }

    private fun countSolutions(springs: String, numbers: List<Int>): Long {
        if (numbers.isEmpty()) {
            return if (springs.contains("#")) 0 else 1
        }
        if (springs.isEmpty()) {
            return 0
        }

        return memo.getOrPut(Pair(springs, numbers)) {
            var count = 0L

            if (canBeAHealthySpring(springs.first())) {
                count += countSolutions(springs.drop(1), numbers)
            }
            if (canStartWithAGroupOfBrokenSprings(springs, numbers.first())) {
                count += countSolutions(springs.drop(numbers.first() + 1), numbers.drop(1))
            }
            count
        }
    }

    private fun canBeAHealthySpring(spring: Char): Boolean {
        return spring != '#'
    }

    private fun canStartWithAGroupOfBrokenSprings(springs: String, length: Int): Boolean {
        val enoughSize = springs.length >= length
        val hasNoHealthySprings = !springs.take(length).contains('.')
        val hasSameSize = length == springs.length
        val nextSpringIsNotBroken = springs.getOrNull(length) != '#'

        return enoughSize && hasNoHealthySprings && (hasSameSize || nextSpringIsNotBroken)
    }


    fun part2(input: List<String>): Long {
        return input.map { line ->
            val springs = "${line.split(" ")[0]}?"
                .repeat(5)
                .dropLast(1)
            val numbers = "${line.split(" ")[1]},"
                .repeat(5)
                .split(",")
                .filter { it.isNotBlank() }
                .map { it.toInt() }

            Pair(springs, numbers)
        }.sumOf { countSolutions(it.first, it.second) }
    }
}

