package year2023

fun main() {
    val input = readInput("Day24")
    Day24.part1(input, 200_000_000_000_000L, 400_000_000_000_000L).println()
    Day24.part2().println()
}

object Day24 {
    fun part1(input: List<String>, atLeast: Long, atMost: Long): Int {
        return input
            .getHailstones()
            .howManyIntersections(atLeast, atMost)
    }

    data class Location(val x: Long, val y: Long, val z: Long)
    data class Velocity(val dx: Long, val dy: Long, val dz: Long)

    private data class Hailstone(
        val location: Location,
        val velocity: Velocity
    ) {

        val slopeXY = velocity.dy / velocity.dx.toDouble()
        val a = -slopeXY
        private val c = -slopeXY * location.x + location.y

        fun intersectionXY(other: Hailstone): Pair<Double, Double>? {
            val determinant = this.a - other.a
            if (0.0 == determinant) {
                return null
            }
            return Pair(
                (this.c - other.c) / determinant,
                (this.a * other.c - other.a * this.c) / determinant
            )
        }

        fun isFutureXY(xy: Pair<Double, Double>): Boolean {
            return ((xy.first - location.x) * velocity.dx) >= 0
                    && ((xy.second - location.y) * velocity.dy >= 0)
        }
    }

    private fun List<String>.getHailstones(): List<Hailstone> {
        return this.map { line -> line.split("@") }
            .map { line ->
                val location = line[0].split(",").map { it.trim().toLong() }
                val velocity = line[1].split(",").map { it.trim().toLong() }
                Hailstone(
                    Location(location[0], location[1], location[2]),
                    Velocity(velocity[0], velocity[1], velocity[2])
                )
            }
    }

    private fun List<Hailstone>.howManyIntersections(atLeast: Long, atMost: Long): Int {
        var count = 0
        for (i in this.indices) {
            for (j in i + 1..<this.size) {
                val hailstone1 = this[i]
                val hailstone2 = this[j]
                if (hailstone1.slopeXY == hailstone2.slopeXY) {
                    continue
                }
                val intersect = hailstone1.intersectionXY(hailstone2)!!
                if (hailstone1.isFutureXY(intersect) && hailstone2.isFutureXY(intersect) &&
                    atLeast <= intersect.first && intersect.first <= atMost &&
                    atLeast <= intersect.second && intersect.second <= atMost
                ) {
                    count++
                }
            }
        }
        return count
    }

    fun part2(): String {
        return "Resolved in Python using Z3 check resources/year2023/Day24_part2.py"
    }
}

