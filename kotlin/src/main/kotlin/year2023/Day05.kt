package year2023

fun main() {
    val input = readInput2("Day05")
    Day05.part1(input).println()
    Day05.part2(input).println()
}

private typealias SeedRange = Pair<Long, Long>
private typealias Decoder = Set<RangeDecoder>
private typealias Decoders = List<Decoder>

private data class RangeDecoder(val from: Long, val quantity: Long, val to: Long)

object Day05 {
    fun part1(input: String): Long {
        val inputChunks = input.split(DOUBLE_NEW_LINE_REGEX)
        val seeds = inputChunks[0]
            .split(":")[1]
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.toLong() }

        val decoders = listOf(
            buildDecoder(inputChunks[1].split(NEW_LINE_REGEX)),
            buildDecoder(inputChunks[2].split(NEW_LINE_REGEX)),
            buildDecoder(inputChunks[3].split(NEW_LINE_REGEX)),
            buildDecoder(inputChunks[4].split(NEW_LINE_REGEX)),
            buildDecoder(inputChunks[5].split(NEW_LINE_REGEX)),
            buildDecoder(inputChunks[6].split(NEW_LINE_REGEX)),
            buildDecoder(inputChunks[7].split(NEW_LINE_REGEX))
        )

        return seeds
            .asSequence()
            .map { decoders.decode(it) }
            .min()
    }

    fun part2(input: String): Long {
        val inputChunks = input.split(DOUBLE_NEW_LINE_REGEX)
        val rangedSeeds = inputChunks[0]
            .split(":")[1]
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.toLong() }
            .chunked(2)
            .map { Pair(it[0], it[0] + it[1]) }

        val decoders = listOf(
            buildDecoder(inputChunks[1].split(NEW_LINE_REGEX)),
            buildDecoder(inputChunks[2].split(NEW_LINE_REGEX)),
            buildDecoder(inputChunks[3].split(NEW_LINE_REGEX)),
            buildDecoder(inputChunks[4].split(NEW_LINE_REGEX)),
            buildDecoder(inputChunks[5].split(NEW_LINE_REGEX)),
            buildDecoder(inputChunks[6].split(NEW_LINE_REGEX)),
            buildDecoder(inputChunks[7].split(NEW_LINE_REGEX))
        )

        return decoders
            .decode(rangedSeeds)
            .minOfOrNull { it.first }
            ?: -1L
    }

    private fun buildDecoder(lines: List<String>): Set<RangeDecoder> {
        return lines
            .drop(1)
            .map {
                val (a, b, c) = it.split(" ")
                RangeDecoder(
                    to = a.toLong(),
                    from = b.toLong(),
                    quantity = c.toLong()
                )
            }
            .toSet()
    }

    private fun RangeDecoder.decode(value: Long): Long {
        return to + value - from
    }

    private fun Decoder.decode(value: Long): Long {
        return this
            .firstOrNull {
                val (from, quantity, _) = it
                val end = from + quantity
                value in from..end
            }
            ?.decode(value)
            ?: value
    }

    private fun Decoders.decode(value: Long): Long {
        return this.fold(value) { acc, decoder ->
            decoder.decode(acc)
        }
    }

    private fun Decoders.decode(rangedSeeds: List<SeedRange>): List<SeedRange> {
        return this.fold(rangedSeeds) { acc, decoder ->
            decoder.decode(acc)
        }
    }

    private fun Decoder.decode(rangedSeeds: List<SeedRange>): List<SeedRange> {
        val consumedSeeds = mutableListOf<SeedRange>()
        val newSeeds = mutableListOf<SeedRange>()
        for (rangeDecoder in this) {
            val (from, quantity, _) = rangeDecoder
            for (range in rangedSeeds) {
                val intersectionMin = maxOf(from, range.first)
                val intersectionMax = minOf(from + quantity, range.second)
                if (intersectionMin > intersectionMax) {
                    continue
                }
                val consumed = Pair(intersectionMin, intersectionMax)
                val newSeed = Pair(rangeDecoder.decode(intersectionMin), rangeDecoder.decode(intersectionMax))
                //println("$consumed -> $newSeed")
                consumedSeeds.add(consumed)
                newSeeds.add(newSeed)
            }
        }

        val normalizedConsumedSeeds = consumedSeeds.distinct().normalize()
        val normalizedRangedSeeds = rangedSeeds.normalize()
        val nonConsumedSeeds = normalizedRangedSeeds.removeRanges(normalizedConsumedSeeds)
        //nonConsumedSeeds.forEach { println(it) }
        //println("")
        return (nonConsumedSeeds + newSeeds).normalize()
    }

    fun List<SeedRange>.removeRanges(toRemove: List<SeedRange>): List<SeedRange> {
        val result = this.toMutableList()

        var pivot1 = 0
        var pivot2 = 0

        while (pivot1 < result.size && pivot2 < toRemove.size) {
            val range1 = result[pivot1]
            val range2 = toRemove[pivot2]

            if (range1.second < range2.first) {
                //range1 outside range2 by the left
                pivot1++
            } else if (range1.first > range2.second) {
                //range1 outside range2 by the right
                pivot2++
            } else if (range1.first < range2.first && range1.second <= range2.second) {
                // range 1 has a remaining part at left
                result.removeAt(pivot1)
                result.add(pivot1, Pair(range1.first, range2.first - 1))
                pivot1++
            } else if (range1.first >= range2.first && range1.second <= range2.second) {
                //range1 is fully contained in range2
                result.removeAt(pivot1)
            } else if (range1.first >= range2.first && range1.second > range2.second) {
                // range 1 has a remaining part at right
                result.removeAt(pivot1)
                result.add(pivot1, Pair(range2.second + 1, range1.second))
                pivot2++
            } else {
                //range 1 has a remaining part at left and right
                result.removeAt(pivot1)
                result.add(pivot1, Pair(range1.first, range2.first - 1))
                result.add(pivot1 + 1, Pair(range2.second + 1, range1.second))
                pivot2++
            }
        }

        return result
    }

    fun List<SeedRange>.normalize(): List<SeedRange> {
        if (this.isEmpty()) {
            return emptyList()
        }

        val sortedRanges = this.sortedBy { it.first }

        val result = mutableListOf<SeedRange>()
        var currentRange = sortedRanges[0]

        for (i in 1 until sortedRanges.size) {
            val nextRange = sortedRanges[i]

            if (currentRange.second >= (nextRange.first - 1)) {
                currentRange = Pair(currentRange.first, maxOf(currentRange.second, nextRange.second))
            } else {
                result.add(currentRange)
                currentRange = nextRange
            }
        }

        result.add(currentRange)

        return result
    }
}

