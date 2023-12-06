package nerok.aoc.aoc2023.day03

import nerok.aoc.utils.Input
import nerok.aoc.utils.Point
import nerok.aoc.utils.append
import nerok.aoc.utils.createCharGrid
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Long {
        val engineSchematic = createCharGrid(input)
        val validPartNumbers = emptyList<Long>().toMutableList()
        var currentPartNumber = ""
        var currentPartNumberValid = false
        engineSchematic.rows.forEach { row ->
            row.forEach { point ->
                if (point.content.isDigit()) {
                    currentPartNumber = "$currentPartNumber${point.content}"
                    engineSchematic.neighbours(point).forEach { neighbour ->
                        if ((!neighbour.content.isDigit()) and (neighbour.content != '.')) {
                            currentPartNumberValid = true
                        }
                    }
                } else {
                    if (currentPartNumberValid) {
                        validPartNumbers.append(currentPartNumber.toLong())
                    }
                    currentPartNumber = ""
                    currentPartNumberValid = false
                }
            }
        }
        return validPartNumbers.sum()
    }

    fun part2(input: List<String>): Long {
        val engineSchematic = createCharGrid(input)
        val validPartNumbers = emptyList<Pair<Point<Char>,Long>>().toMutableList()
        var currentPartNumber = ""
        var currentPartNumberSymbol: Point<Char>? = null
        engineSchematic.rows.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, point ->
                if (point.content.isDigit()) {
                    currentPartNumber = "$currentPartNumber${point.content}"
                    engineSchematic.neighbours(point).forEach { neighbour ->
                        if ((!neighbour.content.isDigit()) and (neighbour.content != '.')) {
                            currentPartNumberSymbol = neighbour
                        }
                    }
                } else {
                    if (currentPartNumberSymbol != null) {
                        validPartNumbers.append(currentPartNumberSymbol!! to currentPartNumber.toLong())
                    }
                    currentPartNumber = ""
                    currentPartNumberSymbol = null
                }
            }
        }
        return validPartNumbers
            .groupBy { it.first }
            .filterKeys { it.content == '*' }
            .filterValues { it.size > 1 }
            .map {
                it.key to it.value.map { it.second }.reduce { acc, l -> acc * l }
            }
            .sumOf { it.second }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.readInput("Day03_test")
    check(part1(testInput) == 4361L)
    check(part2(testInput) == 467835L)

    val input = Input.readInput("Day03")
    println("Part1:")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println("Part2:")
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
