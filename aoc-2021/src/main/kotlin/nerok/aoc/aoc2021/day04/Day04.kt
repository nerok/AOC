package nerok.aoc.aoc2021.day04

import nerok.aoc.utils.Input
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    fun calculateScore(bingoBoard: List<MutableList<Int>>, drawing: List<Int>): Pair<Int, Int> {
        drawing.forEachIndexed { drawNumber, draw ->
            bingoBoard.mapIndexed { index, ints ->
                if (ints.contains(draw)) {
                    index to ints.indexOf(draw)
                }
                else {
                    null
                }
            }.filterNotNull().forEach { coordinate ->
                bingoBoard[coordinate.first][coordinate.second] = 0
                if (bingoBoard[coordinate.first].none { it != 0 }) {
                    return drawNumber to bingoBoard.sumOf { it.sum() } * drawing[drawNumber]
                }
                else if (bingoBoard.map { row -> row[coordinate.second] }.none { it != 0 }) {
                    return drawNumber to bingoBoard.sumOf { it.sum() } * drawing[drawNumber]
                }
            }
        }
        return 0 to 0
    }

    fun part1(input: List<String>): Long {
        val drawings = input.first().split(",").map { it.toInt() }
        return input.drop(1).asSequence()
            .windowed(6, step = 6)
            .map { board ->
                board.filter { it.isNotEmpty() }
            }
            .map { board ->
                board.map { row ->
                    row.split(" ")
                        .filter { it.isNotEmpty() }
                        .map { it.toInt() }
                        .toMutableList()
                }
            }
            .map { board ->
                calculateScore(board, drawings)
            }
            .minBy { it.first }.second.toLong()
    }

    fun part2(input: List<String>): Long {
        val drawings = input.first().split(",").map { it.toInt() }
        return input.drop(1).asSequence()
            .windowed(6, step = 6)
            .map { board ->
                board.filter { it.isNotEmpty() }
            }
            .map { board ->
                board.map { row ->
                    row.split(" ")
                        .filter { it.isNotEmpty() }
                        .map { it.toInt() }
                        .toMutableList()
                }
            }
            .map { board ->
                calculateScore(board, drawings)
            }
            .maxBy { it.first }.second.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.readInput("Day04_test")
    check(part1(testInput) == 4512L)
    check(part2(testInput) == 1924L)

    val input = Input.readInput("Day04")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
