package nerok.aoc.aoc2023.day09

import nerok.aoc.utils.Input
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Long {
        return input.size.toLong()
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.readInput("Day09_test")
    check(part1(testInput) == 1L)
    check(part2(testInput) == 2L)

    val input = Input.readInput("Day09")
    println("Part1:")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println("Part2:")
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
