package year2023

fun main() {
    val input = readInput2("Day19")
    Day19.part1(input).println()
    Day19.part2(input).println()
}

object Day19 {
    fun part1(input: String): Int {
        val chunks = input.split(DOUBLE_NEW_LINE_REGEX)

        val workflows = chunks[0]
            .split(NEW_LINE_REGEX)
            .map { line ->
                val name = line.split("{")[0]
                val rules = line.split("{")[1].split("}")[0].split(",").map { Rule(it) }
                Workflow(name, rules)
            }
            .associateBy { it.name }

        val ratings = chunks[1]
            .split(NEW_LINE_REGEX)
            .map { line ->
                line.split("{")[1]
                    .split("}")[0]
                    .split(",")
                    .associate {
                        it.split("=")[0][0] to it.split("=")[1].toInt()
                    }
            }

        return ratings.sumOf { rating ->
            var result = -1
            var next = "in"
            while (result == -1) {
                val rule = workflows.getValue(next).rules.first { it.matches(rating) }
                when (rule.result) {
                    "R" -> result = 0
                    "A" -> result = rating.values.sum()
                    else -> next = rule.result
                }
            }
            result
        }
    }

    fun part2(input: String): Long {
        val chunks = input.split(DOUBLE_NEW_LINE_REGEX)

        val workflows = chunks[0]
            .split(NEW_LINE_REGEX)
            .map { line ->
                val name = line.split("{")[0]
                val rules = line.split("{")[1].split("}")[0].split(",").map { Rule(it) }
                Workflow(name, rules)
            }
            .associateBy { it.name }

        return combine(
            workflows = workflows,
            entry = "in",
            ranges = mapOf(
                'x' to (1..4000),
                'm' to (1..4000),
                'a' to (1..4000),
                's' to (1..4000)
            )
        )
    }

    private fun combine(workflows: Map<String, Workflow>, entry: String, ranges: Map<Char, IntRange>): Long {
        return when (entry) {
            "R" -> 0L
            "A" -> ranges.values.map { it.last() - it.first() + 1 }.map { it.toLong() }.reduce(Long::times)
            else -> {
                val newRanges = ranges.toMutableMap()
                workflows.getValue(entry).rules.sumOf { rule ->
                    if (rule.isUnconditional()) {
                        combine(
                            workflows = workflows,
                            entry = rule.result,
                            ranges = newRanges
                        )
                    } else {
                        val newRange = newRanges.getValue(rule.category!!).intersection(rule.range())
                        val newReversed = newRanges.getValue(rule.category).intersection(rule.reversedRange())

                        newRanges[rule.category] = newRange
                        combine(
                            workflows = workflows,
                            entry = rule.result,
                            ranges = newRanges
                        ).also { newRanges[rule.category] = newReversed }
                    }
                }
            }
        }
    }

    data class Workflow(val name: String, val rules: List<Rule>)

    class Rule(rule: String) {
        val result: String
        val category: Char?
        val operand: Char?
        private val value: Int?

        init {
            if (':' in rule) {
                val condition = rule.split(":")[0]
                category = condition[0]
                operand = condition[1]
                value = condition.substring(2).toInt()
                result = rule.split(":")[1]
            } else {
                category = null
                operand = null
                value = null
                result = rule
            }
        }

        fun isUnconditional() = category == null || operand == null || value == null

        fun matches(rating: Map<Char, Int>): Boolean {
            if (isUnconditional()) {
                return true
            }
            return when (operand) {
                '>' -> rating.getValue(category!!) > value!!
                '<' -> rating.getValue(category!!) < value!!
                else -> false
            }
        }

        fun range(): IntRange {
            if (value == null) {
                error("Not defined")
            }
            if (operand == '<') {
                return (1..<value)
            }
            return (value + 1..4000)
        }

        fun reversedRange(): IntRange {
            if (value == null) {
                error("Not defined")
            }
            if (operand == '<') {
                return (value..4000)
            }
            return (1..value)
        }
    }

    private fun IntRange.intersection(other: IntRange): IntRange {
        return (maxOf(first, other.first)..minOf(last, other.last))
    }
}

