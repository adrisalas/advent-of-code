package year2023

fun main() {
    val input = readInput("Day08")
    Day08.part1(input).println()
    Day08.part2(input).println()
}

object Day08 {
    fun part1(input: List<String>): Int {
        val instructions = input[0].map { if (it == 'L') 0 else 1 }
        val map = input
            .drop(2).map {
                val (key, rest) = it.split(" = (")
                val value = Pair(rest.substring(0, 3), rest.substring(5, 8))

                key to value
            }.toMap()

        var iterator = 0
        var key = "AAA"
        while (key != "ZZZ") {
            val leftOrRight = instructions[iterator % instructions.size]

            key = if (leftOrRight == 0) {
                map[key]?.first ?: key
            } else {
                map[key]?.second ?: key
            }

            iterator++
        }

        return iterator
    }

    fun part2(input: List<String>): Long {
        val instructions = input[0].map { if (it == 'L') 0 else 1 }
        val map = input
            .drop(2).map {
                val (key, rest) = it.split(" = (")
                val value = Pair(rest.substring(0, 3), rest.substring(5, 8))

                key to value
            }.toMap()

        var startingGhosts = map.keys.filter { it.endsWith("A") }

        return startingGhosts
            .map {
                var iterator = 0L
                var key = it
                while (!key.endsWith("Z")) {
                    val leftOrRight = instructions[(iterator % instructions.size).toInt()]

                    key = if (leftOrRight == 0) {
                        map[key]?.first ?: key
                    } else {
                        map[key]?.second ?: key
                    }

                    iterator++
                }
                iterator
            }.lcm()
    }

    private fun List<Long>.lcm(): Long {
        if (this.isEmpty()) {
            return 0
        }
        if (this.size == 1) {
            return this[0]
        }
        var result = this[0]
        for (i in 1 until this.size) {
            result = lcm(result, this[i])
        }
        return result
    }

    private fun lcm(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }
}

