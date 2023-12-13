package year2023

fun main() {
    val input = readInput2("Day13")
    Day13.part1(input).println()
    Day13.part2(input).println()
}

object Day13 {
    fun part1(input: String): Int {
        val inputChunks = input.split(DOUBLE_NEW_LINE_REGEX)

        return inputChunks
            .map { it.split(NEW_LINE_REGEX) }
            .sumOf {
                val horizontal = it.horizontalReflection()
                val vertical = it.verticalReflection()

                if (horizontal != -1) (horizontal + 1) * 100 else (vertical + 1)
            }
    }

    fun part2(input: String): Int {
        val inputChunks = input.split(DOUBLE_NEW_LINE_REGEX)

        return inputChunks
            .map { it.split(NEW_LINE_REGEX) }
            .sumOf {
                val horizontal = it.horizontalReflection(smudgesDesired = 1)
                val vertical = it.verticalReflection(smudgesDesired = 1)

                if (horizontal != -1) (horizontal + 1) * 100 else (vertical + 1)
            }
    }

    private fun List<String>.horizontalReflection(smudgesDesired: Int = 0): Int {
        for (row in 0 until this.size - 1) {
            if (hasHorizontalReflectionIn(row, smudgesDesired)) {
                return row
            }
        }
        return -1
    }

    private fun List<String>.hasHorizontalReflectionIn(row: Int, smudgesDesired: Int): Boolean {
        var left = row
        var right = row + 1
        var smudges = 0

        while (left >= 0 && right < this.size) {
            smudges += this[left].countDifferentChars(this[right])

            left--
            right++
        }

        return smudges == smudgesDesired
    }

    private fun String.countDifferentChars(other: String): Int {
        assert(this.length == other.length) { "It should have the same size" }

        var count = 0

        for (i in this.indices) {
            if (this[i] != other[i]) {
                count++
            }
        }

        return count
    }

    private fun List<String>.verticalReflection(smudgesDesired: Int = 0): Int {
        for (column in 0 until this[0].length - 1) {
            if (hasVerticalReflectionIn(column, smudgesDesired)) {
                return column
            }
        }
        return -1
    }

    private fun List<String>.hasVerticalReflectionIn(column: Int, smudgesDesired: Int): Boolean {
        var top = column
        var down = column + 1
        var smudges = 0

        while (top >= 0 && down < this[0].length) {
            val topBuilder = StringBuilder()
            val downBuilder = StringBuilder()

            for (row in this.indices) {
                topBuilder.append(this[row][top])
                downBuilder.append(this[row][down])
            }

            smudges += topBuilder.toString().countDifferentChars(downBuilder.toString())

            top--
            down++
        }

        return smudges == smudgesDesired
    }
}

