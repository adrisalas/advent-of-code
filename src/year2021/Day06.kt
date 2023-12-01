package year2021

private fun solve(input: String, days: Int): Long {
    val lanternFishes = lanternFishes(input)

    simulateDays(lanternFishes, days)

    return lanternFishes.countLanterfishs()
}

private fun part1(input: String): Long {
    return solve(input, 80)
}

private fun part2(input: String): Long {
    return solve(input, 256)
}

private fun lanternFishes(input: String): MutableMap<Int, Long> {
    val lanternFishes: MutableMap<Int, Long> = HashMap()

    input
        .split(",")
        .map { Integer.valueOf(it) }
        .forEach { lanternFishes[it] = lanternFishes.getOrDefault(it, 0) + 1 }
    
    return lanternFishes
}

private fun MutableMap<Int, Long>.countLanterfishs(): Long {
    return this.values.fold(0L) { acc, i -> acc + i }
}

private fun simulateDays(lanternfishs: MutableMap<Int, Long>, days: Int) {
    for (i in 0 until days) {
        val reproduced: Long = lanternfishs.getOrDefault(0, 0L)
        lanternfishs[0] = lanternfishs.getOrDefault(1, 0L)
        lanternfishs[1] = lanternfishs.getOrDefault(2, 0L)
        lanternfishs[2] = lanternfishs.getOrDefault(3, 0L)
        lanternfishs[3] = lanternfishs.getOrDefault(4, 0L)
        lanternfishs[4] = lanternfishs.getOrDefault(5, 0L)
        lanternfishs[5] = lanternfishs.getOrDefault(6, 0L)
        lanternfishs[6] = lanternfishs.getOrDefault(7, 0L) + reproduced
        lanternfishs[7] = lanternfishs.getOrDefault(8, 0L)
        lanternfishs[8] = reproduced
    }
}

fun main() {
    val testInput = readInput("Day06_test")[0]
    check(solve(testInput, 18).toInt() == 26)

    val input = readInput("Day06")[0]
    println(part1(input))
    println(part2(input))
}

