package nerok.aoc.aoc2021.day02

import nerok.aoc.utils.Input
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Long {
        var depth = 0
        var horizontal = 0
        input.forEach {
            val (command, value) = it.split(" ")
            when (command) {
                "forward" -> horizontal = horizontal.plus(value.toInt())
                "down" -> depth = depth.plus(value.toInt())
                "up" -> depth = depth.minus(value.toInt())
            }
        }
        println("Horizontal: $horizontal")
        println("Depth: $depth")
        println("Score: ${depth*horizontal}")
        return (depth*horizontal).toLong()
    }

    fun part2(input: List<String>): Long {
        var depth = 0
        var horizontal = 0
        var aim = 0
        input.forEach {
            val (command, value) = it.split(" ")
            when (command) {
                "forward" -> {
                    horizontal = horizontal.plus(value.toInt())
                    depth = depth.plus(aim.times(value.toInt()))
                }
                "down" -> aim = aim.plus(value.toInt())
                "up" -> aim = aim.minus(value.toInt())
            }
        }
        println("Horizontal: $horizontal")
        println("Depth: $depth")
        println("Score: ${depth*horizontal}")
        return (depth*horizontal).toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.readInput("Day02_test")
    check(part1(testInput) == 150L)
    check(part2(testInput) == 900L)

    val input = Input.readInput("Day02")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
