package year2023

fun main() {
    val input = readInput2("Day15")
    Day15.part1(input).println()
    Day15.part2(input).println()
}

object Day15 {
    fun part1(input: String): Int {
        return input
            .split(",")
            .sumOf { it.customHash() }
    }

    fun part2(input: String): Int {
        val sequence = input.split(",")

        val boxes = List(256) { mutableMapOf<String, Int>() }

        sequence.forEach { line ->
            val (value, length) = line.split("=", "-")
            if (line.contains("-")) {
                boxes[value.customHash()] -= value
            } else {
                boxes[value.customHash()][value] = length.toInt()
            }
        }

        return boxes
            .mapIndexed { boxNumber, box ->
                val boxPlusOne = (boxNumber + 1)
                val slotPlusOnePerLength = box.values
                    .mapIndexed { slotNumber, length -> (slotNumber + 1) * length }
                    .sum()
                boxPlusOne * slotPlusOnePerLength
            }
            .sum()
    }
    
    private fun String.customHash(): Int {
        return this.fold(0) { hash, c ->
            ((hash + c.code) * 17) % 256
        }
    }
}

