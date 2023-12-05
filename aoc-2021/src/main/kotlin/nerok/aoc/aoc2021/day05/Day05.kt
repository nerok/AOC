package nerok.aoc.aoc2021.day05

import nerok.aoc.utils.Input
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Long =
        input.map { Integer.parseInt(it) }.windowed(2) {
            it.first() < it.last()
        }.count { it }.toLong()

    fun part2(input: List<String>): Long = input.map { Integer.parseInt(it) }.windowed(3) {
        it.sum()
    }.windowed(2){
        it.first() < it.last()
    }.count { it }.toLong()

    // test if implementation meets criteria from the description, like:
    val testInput = Input.readInput("Day05_test")
    check(part1(testInput) == 7L)
    check(part2(testInput) == 5L)

    val input = Input.readInput("Day05")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
