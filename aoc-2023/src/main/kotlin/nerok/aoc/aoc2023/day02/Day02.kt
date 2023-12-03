package nerok.aoc.aoc2023.day02

import nerok.aoc.utils.Input
import kotlin.time.DurationUnit
import kotlin.time.measureTime

data class RGB(
    val red: Long,
    val green: Long,
    val blue: Long
) {
    fun mergeMax(other: RGB): RGB {
        return RGB(
            red = maxOf(this.red, other.red),
            green = maxOf(this.green, other.green),
            blue = maxOf(this.blue, other.blue),
        )
    }

    fun aboveLimit(limit: RGB): Boolean {
        if (this.red > limit.red) return true
        if (this.green > limit.green) return true
        if (this.blue > limit.blue) return true
        return false
    }

    fun power(): Long {
        return this.red * this.green * this.blue
    }
}

fun String.toRGB(): RGB {
    var red = 0L
    var green = 0L
    var blue = 0L
    this
        .split(",")
        .map { it.trim() }
        .map {
        if (it.contains("red")) {
            red = it.split(" ").first().toLongOrNull() ?: 0L
        }
        if (it.contains("green")) {
            green = it.split(" ").first().toLongOrNull() ?: 0L
        }
        if (it.contains("blue")) {
            blue = it.split(" ").first().toLongOrNull() ?: 0L
        }
    }
    return RGB(red, green, blue)
}

fun main() {
    fun part1(input: List<String>): Long {
        val limit = RGB(12,13,14)
        return input.map { game ->
            val parsed = game.split(":")
            val gameid = parsed.first().removePrefix("Game ").toLong()
            val data = parsed.last().split(";").map { c ->
                c.toRGB()
            }.reduce { acc, rgb -> acc.mergeMax(rgb) }
            gameid to data
        }.filterNot {
            it.second.aboveLimit(limit)
        }.sumOf { it.first }
    }

    fun part2(input: List<String>): Long {
        return input.map { game ->
            val data = game
                .split(":")
                .last()
                .split(";")
                .map { c ->
                    c.toRGB()
                }
                .reduce { acc, rgb -> acc.mergeMax(rgb) }
            data.power()
        }.sumOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.readInput("Day02_test")
    check(part1(testInput) == 8L)
    check(part2(testInput) == 2286L)

    val input = Input.readInput("Day02")
    println("Part1:")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println("Part2:")
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
