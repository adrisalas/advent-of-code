package year2023

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun readInput(name: String): List<String> {
    return Path("src/test/resources/year2023/$name.txt").readLines()
}
