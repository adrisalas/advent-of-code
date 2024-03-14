package year2021

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

fun readInput(name: String): List<String> {
    return Path("src/main/resources/year2021/$name.txt").readLines()
}

fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)


fun Any?.println() {
    println(this)
}
