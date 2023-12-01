package nerok.aoc.aoc2022.day06

import nerok.aoc.utils.Input
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    fun part1(input: String): Long {
        val startOfPacketLength = 4
        return input
            .windowed(startOfPacketLength)
            .map { substrings -> substrings.groupBy { it }.size }
            .indexOf(startOfPacketLength).toLong().plus(startOfPacketLength)
    }

    fun part2(input: String): Long {
        val startOfPacketLength = 14
        return input
            .windowed(startOfPacketLength)
            .map { substrings -> substrings.groupBy { it }.size }
            .indexOf(startOfPacketLength).toLong().plus(startOfPacketLength)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.getInput("Day06_test")
    println(part1(testInput))
    check(part1(testInput) == 7L)
    check(part2(testInput) == 19L)

    val input = Input.getInput("Day06")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
