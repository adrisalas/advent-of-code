package year2023

import java.util.*

fun main() {
    val input = readInput("Day09")
    Day09.part1(input).println()
    Day09.part2(input).println()
}

object Day09 {
    fun part1(input: List<String>): Long {
        return input
            .map { line -> toIntList(line) }
            .sumOf { history -> predictionLastNumber(history) }
    }

    private fun toIntList(line: String): List<Int> {
        return line.split(" ")
            .filter { it.isNotBlank() }
            .map { it.toInt() }
    }

    private fun predictionLastNumber(history: List<Int>): Long {
        val incrementStack = Stack<List<Int>>()
        incrementStack.push(history)

        while (!incrementStack.peek().all { it == 0 }) {
            val increment = incrementStack
                .peek()
                .windowed(2)
                .map { it[1] - it[0] }
            incrementStack.push(increment)
        }

        var prediction = 0L
        while (incrementStack.isNotEmpty()) {
            val increment = incrementStack.pop()
            prediction += increment.last()
        }

        return prediction
    }

    fun part2(input: List<String>): Long {
        return input
            .map { line -> toIntList(line) }
            .sumOf { history -> predictionFirstNumber(history) }
    }

    private fun predictionFirstNumber(history: List<Int>): Long {
        val incrementStack = Stack<List<Int>>()
        incrementStack.push(history)

        while (!incrementStack.peek().all { it == 0 }) {
            val increment = incrementStack
                .peek()
                .windowed(2)
                .map { it[1] - it[0] }
            incrementStack.push(increment)
        }

        var prediction = 0L
        while (incrementStack.isNotEmpty()) {
            val increment = incrementStack.pop()
            prediction = increment.first() - prediction
        }

        return prediction
    }
}

