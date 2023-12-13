package year2023

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

val NEW_LINE_REGEX = "\r?\n".toRegex()
val DOUBLE_NEW_LINE_REGEX = "(\r)?\n(\r)?\n".toRegex()

fun readInput(name: String): List<String> {
    return Path("src/main/resources/year2023/$name.txt").readLines()
}

fun readInput2(name: String): String {
    return Path("src/main/resources/year2023/$name.txt").readText()
}

fun String.md5(): String {
    return BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')
}

fun Any?.println() {
    println(this)
}
