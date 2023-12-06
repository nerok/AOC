package nerok.aoc.aoc2021.day07

import nerok.aoc.utils.Input
import kotlin.math.abs
import kotlin.math.nextDown
import kotlin.math.roundToInt
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    fun calculateFuelCosts(positions: MutableList<Long>, selectedPosition: Long): Long {
        var cost = 0L
        positions.forEach {
            cost += abs(it - selectedPosition)
        }
        return cost
    }

    fun calculateProgressiveFuelCosts(positions: MutableList<Long>, selectedPosition: Long): Long {
        var cost = 0L
        positions.forEach {
            cost += LongRange(0, abs(it - selectedPosition)).sum()
        }
        return cost
    }

    fun part1(input: List<String>): Long {
        val longs = input.first().split(",").map { it.toLong() }.toMutableList()
        return calculateFuelCosts(longs, longs.sorted()[(longs.size/2)])
    }

    // Broken
    fun part2(input: List<String>): Long {
        val longs = input.first().split(",").map { it.toLong() }.toMutableList()
        val average = longs.average().nextDown().toLong()
        return calculateProgressiveFuelCosts(longs, average)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.readInput("Day07_test")
    check(part1(testInput) == 37L)
    //check(part2(testInput) == 168L)

    val input = Input.readInput("Day07")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
