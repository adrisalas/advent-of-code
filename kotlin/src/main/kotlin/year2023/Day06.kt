package year2023

fun main() {
    val input = readInput("Day06")
    Day06.part1(input).println()
    Day06.part2(input).println()
}

object Day06 {
    fun part1(input: List<String>): Int {
        val times = input[0].split(":")[1].split(" ").filter { it.isNotBlank() }
        val distances = input[1].split(":")[1].split(" ").filter { it.isNotBlank() }

        return times.mapIndexed { i, it ->
            val maxTime = it.toInt()
            val objective = distances[i].toInt()
            var count = 0
            for (time in 0..maxTime) {
                val distance = (maxTime - time) * time
                if (distance > objective) {
                    count++
                }
            }
            count
        }.fold(1) { acc, count ->
            acc * count
        }
    }

    fun part2(input: List<String>): Int {
        val maxTime = input[0]
            .split(":")[1]
            .split(" ")
            .filter { it.isNotBlank() }
            .joinToString(separator = "")
            .toLong()
        val objective = input[1]
            .split(":")[1]
            .split(" ")
            .filter { it.isNotBlank() }
            .joinToString(separator = "")
            .toLong()

        var count = 0
        for (time in 0..maxTime) {
            val distance = (maxTime - time) * time
            if (distance > objective) {
                count++
            }
        }
        return count
    }
}

