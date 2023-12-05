package year2023

import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

fun readInput(name: String): List<String> {
    return Path("src/test/resources/year2023/$name.txt").readLines()
}

fun readInput2(name: String): String {
    return Path("src/test/resources/year2023/$name.txt").readText()
}
