package nerok.aoc.aoc2023.day01

import nerok.aoc.utils.Input
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Long {
        return input.map { line ->
            line.filter { it.isDigit() }
        }.sumOf { "${it.first()}${it.last()}".toLong() }
    }

    fun part2(input: List<String>): Any {
        return input.sumOf { line ->
            "${line.findFirstNumber()}${line.findLastNumber()}".toLong()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = Input.readInput("Day01_test1")
    check(part1(testInput1) == 142L)
    val testInput2 = Input.readInput("Day01_test2")
    check(part2(testInput2) == 281L)

    val input = Input.readInput("Day01")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}

private fun String.findFirstNumber(): String {
    return findAnyOf(
        listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    )!!.second.convertToDigit()
}
private fun String.findLastNumber(): String {
    return findLastAnyOf(
        listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    )!!.second.convertToDigit()
}
private fun String.convertToDigit(): String {
    return replace("one", "1")
        .replace("two", "2")
        .replace("three", "3")
        .replace("four", "4")
        .replace("five", "5")
        .replace("six", "6")
        .replace("seven", "7")
        .replace("eight", "8")
        .replace("nine", "9")
}
