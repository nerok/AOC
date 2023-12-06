package nerok.aoc.aoc2021.day08

import nerok.aoc.utils.Input
import java.util.*
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {

    fun predictPairs(x: List<SortedSet<Char>>): MutableMap<SortedSet<Char>, Int> {
        val map = x.associateWith { -1 }.toMutableMap()
        map.forEach {
            when (it.key.size) {
                2 -> {
                    map[it.key] = 1
                }
                3 -> {
                    map[it.key] = 7
                }
                4 -> {
                    map[it.key] = 4
                }
                7 -> {
                    map[it.key] = 8
                }
                else -> {
                }
            }
        }
        map.filter { it.key.size == 6 }.forEach {
            if (it.key.containsAll(map.filterValues { it == 4 }.keys.first())) {
                map[it.key] = 9
            } else if (it.key.containsAll(map.filterValues { it == 1 }.keys.first())) {
                map[it.key] = 0
            } else {
                map[it.key] = 6
            }
        }
        map.filter { it.value == -1 }.forEach {
            if (it.key.containsAll(map.filterValues { it == 7 }.keys.first())) {
                map[it.key] = 3
            } else if (map.filterValues { it == 9 }.keys.first().containsAll(it.key)) {
                map[it.key] = 5
            } else {
                map[it.key] = 2
            }
        }
        return map
    }

    fun part1(input: List<String>): Long {
        val countArray = IntArray(8) { 0 }
        input.map { line ->
            line.split(" | ").map { it.split(" ") }.let { it.first() to it.last() }
        }.map { entry ->
            entry.first.groupBy { it.length } to entry.second.groupBy { it.length }
        }.map { it.second }.forEach {
            it.forEach {
                countArray[it.key] += it.value.size
            }
        }
        val countMap = countArray.mapIndexed { index, i -> index to i }.toMap()
        return countMap[2]!!.plus(countMap[3]!!).plus(countMap[4]!!).plus(countMap[7]!!).toLong()
    }

    fun part2(input: List<String>): Long = input.map { line ->
            line.split(" | ").map { it.split(" ") }.let { it.first().map { it.toSortedSet() } to it.last().map { it.toSortedSet() } }
        }.map {
            val map = predictPairs(it.first)
            Integer.parseInt(it.second.map { map[it] }.let { it.joinToString("") })
        }.sum().toLong()

    // test if implementation meets criteria from the description, like:
    val testInput = Input.readInput("Day08_test")
    check(part1(testInput) == 26L)
    check(part2(testInput) == 61229L)

    val input = Input.readInput("Day08")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
