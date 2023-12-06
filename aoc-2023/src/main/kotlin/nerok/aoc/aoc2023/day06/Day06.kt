package nerok.aoc.aoc2023.day06

import nerok.aoc.utils.Input
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    fun distances(time: Long): List<Long> {
        val returnList = emptyList<Long>().toMutableList()
        for (i in 0..time) {
            returnList.add((time-i) * i)
        }
        return returnList
    }

    fun part1(input: List<String>): Long {
        val times = input.first().removePrefix("Time:").split(" ").filter { it.isNotBlank() }.map { it.toLong() }
        val distance = input.last().removePrefix("Distance:").split(" ").filter { it.isNotBlank() }.map { it.toLong() }
        return times.zip(distance).map { game -> distances(game.first).filter { it > game.second }.size}.reduce { acc, i -> acc * i }.toLong()
    }

    fun part2(input: List<String>): Long {
        val times = input.first().removePrefix("Time:").filterNot { it.isWhitespace() }.map { it.digitToInt().toLong() }.reduce { acc: Long, l: Long -> (acc * 10) + l }
        val distance = input.last().removePrefix("Distance:").filterNot { it.isWhitespace() }.map { it.digitToInt().toLong() }.reduce { acc: Long, l: Long -> (acc * 10) + l }
        val distanceList = distances(times)
        return distanceList.filter { it > distance }.size.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.readInput("Day06_test")
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)

    val input = Input.readInput("Day06")
    println("Part1:")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println("Part2:")
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
