package nerok.aoc.aoc2021.day06

import nerok.aoc.utils.Input
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    fun part1(input: String): Long {
        var fish = input.split(",").map { it.toInt() }
        val days = 80
        for (i in 1 .. days) {
            val numberOfZeros = fish.count { it == 0 }
            val nextFish = fish.map { if (it == 0) 7 else it }.map { it - 1 }
            fish = nextFish.let { it.plus(IntArray(numberOfZeros) { 8 }.toList()) }
        }
        return fish.size.toLong()
    }

    fun part2(input: String): Long {
        val fish = input.split(",").map { it.toInt() }
        var fishCount = fish.groupBy { it }.map { it.key.toLong() to it.value.count().toULong() }.toMap().toSortedMap()
        val days = 256
        for (i in 1 .. days) {
            val numberOfZeros = fishCount.getOrDefault(0, 0u)
            fishCount = fishCount.map { it.key - 1L to it.value }.toMap().toMutableMap().let {
                it[8] = numberOfZeros
                it[6] = it.getOrDefault(6, 0u).plus(numberOfZeros)
                it
            }.filterKeys { it >= 0 }.toSortedMap()
        }
        println(fishCount)
        return fishCount.values.sum().toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.readInput("Day06_test")
    check(part1(testInput.first()) == 5934L)
    check(part2(testInput.first()) == 26984457539L)

    val input = Input.readInput("Day06")
    println(measureTime { println(part1(input.first())) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input.first())) }.toString(DurationUnit.SECONDS, 3))
}
