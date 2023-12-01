package nerok.aoc.utils

import java.math.BigInteger
import java.security.MessageDigest


object Input {
    private fun inputReader(name: String) = this::class.java
        .getResourceAsStream("/$name.txt")
        ?.bufferedReader(Charsets.UTF_8)
        ?: throw IllegalArgumentException("Cannot find file with name: $name")

    /**
     * Reads lines from the given input txt file.
     */
    fun readInput(name: String) =
        inputReader(name)
        .readLines()

    /**
     * Reads whole input txt file as string
     */
    fun getInput(name: String) =
        inputReader(name)
        .readText()
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun <T> MutableList<T>.prepend(element: T) {
    add(0, element)
}
fun <T> MutableList<T>.append(element: T) {
    add(element)
}

fun <T> MutableCollection<T>.removeFirst() =
    first().also { remove(it) }

fun <T> MutableCollection<T>.removeFirstOrNull() =
    firstOrNull().also { remove(it) }
