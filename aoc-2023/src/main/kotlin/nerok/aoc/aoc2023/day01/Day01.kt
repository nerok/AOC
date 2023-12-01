package nerok.aoc.aoc2023.day01

import nerok.aoc.utils.Input
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Long {
        return input
    }

    fun part2(input: List<String>): Any {
        return input
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = Input.readInput("Day01_test1")
    check(part1(testInput1) == 1L)
    val testInput2 = Input.readInput("Day01_test2")
    check(part2(testInput2) == 2L)

    val input = Input.readInput("Day01")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
