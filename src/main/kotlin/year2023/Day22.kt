package year2023

fun main() {
    val input = readInput("Day22")
    Day22.part1(input).println()
    Day22.part2(input).println()
}

object Day22 {
    fun part1(input: List<String>): Int {
        val bricks = getBricks(input)

        val brickSupportedBy = getSupportedBy(bricks)

        return bricks.size - brickSupportedBy.values
            .filter { it.size == 1 }
            .map { it.toSet() }
            .reduce(Set<Int>::union)
            .size
    }

    fun part2(input: List<String>): Int {
        val bricks = getBricks(input)

        val bricksSupportedBy = getSupportedBy(bricks)
        val bricksSupporting = bricksSupportedBy.reverse()

        return bricks.sumOf { brick ->
            val bricksThatWouldFall = mutableSetOf(brick.id)

            var nextBricksFalling = bricksSupporting[brick.id] ?: emptySet()

            while (nextBricksFalling.isNotEmpty()) {
                val builder = mutableSetOf<Int>()
                for (next in nextBricksFalling) {
                    if ((bricksSupportedBy.getValue(next) - bricksThatWouldFall).isEmpty()) {
                        bricksThatWouldFall += next
                        builder.addAll(bricksSupporting[next] ?: emptySet())
                    }
                }
                nextBricksFalling = builder
            }

            bricksThatWouldFall.size - 1
        }
    }

    data class Brick(val rowRange: IntRange, val columnRange: IntRange, var heightRange: IntRange, val id: Int) {
        fun eagleView(): List<Pair<Int, Int>> {
            return rowRange.flatMap { x ->
                columnRange.map { y -> Pair(x, y) }
            }
        }

        fun startHeightAt(height: Int) {
            heightRange = height..<(height + heightRange.last() - heightRange.first() + 1)
        }
    }

    private fun getBricks(input: List<String>): List<Brick> {
        val bricks = input
            .mapIndexed { id, line ->
                val (part1, part2) = line.split("~")
                val (row1, column1, height1) = part1.split(",").map { it.toInt() }
                val (row2, column2, height2) = part2.split(",").map { it.toInt() }
                Brick(row1..row2, column1..column2, height1..height2, id)
            }
            .sortedBy { it.heightRange.first }
        return bricks
    }

    private fun getSupportedBy(bricks: List<Brick>): Map<Int, Set<Int>> {
        val supportedBy = mutableMapOf<Int, MutableSet<Int>>()
        val surfaceToHeight = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()

        for (brick in bricks) {
            val surface = brick.eagleView()
            val maxHeight = surface.map { surfaceToHeight[it]?.second ?: 0 }.maxOf { it }

            brick.startHeightAt(maxHeight + 1)

            for (point in surface) {
                val (id, height) = surfaceToHeight[point] ?: Pair(-1, 0)
                if (height == maxHeight && id != -1) {
                    supportedBy.getOrPut(brick.id) { mutableSetOf() }.add(id)
                }
                surfaceToHeight[point] = brick.id to brick.heightRange.last
            }
        }
        return supportedBy
    }

    private fun Map<Int, Set<Int>>.reverse(): Map<Int, Set<Int>> {
        val reversed = mutableMapOf<Int, MutableSet<Int>>()

        this.forEach { (key, value) ->
            value.forEach {
                reversed.getOrPut(it) { mutableSetOf() }.add(key)
            }
        }

        return reversed
    }
}

