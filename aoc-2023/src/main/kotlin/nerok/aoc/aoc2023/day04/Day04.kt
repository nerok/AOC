package nerok.aoc.aoc2023.day04

import nerok.aoc.utils.Input
import kotlin.math.pow
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Long {
        return input.map { game ->
            var inWinning = false
            var inPlayers = false
            val winningNumbers = emptySet<Long>().toMutableSet()
            val yourNumbers = emptySet<Long>().toMutableSet()
            var currentNumber = ""
            game.toCharArray().map {
                when {
                    it.isDigit() -> {
                        currentNumber = if (currentNumber.isNotEmpty()) {
                            "$currentNumber$it"
                        } else {
                            it.toString()
                        }
                    }
                    it.isWhitespace() -> {
                        if (currentNumber.isNotEmpty()) {
                            if (inWinning) {
                                winningNumbers.add(currentNumber.toLong())
                            }
                            if (inPlayers) {
                                yourNumbers.add(currentNumber.toLong())
                            }
                            currentNumber = ""
                        }
                    }
                    it == ':' -> {
                        inWinning = true
                        currentNumber = ""
                    }
                    it == '|' -> {inPlayers = true; inWinning=false}
                }
            }
            if (currentNumber.isNotEmpty()) {
                yourNumbers.add(currentNumber.toLong())
            }
            return@map 2.0.pow(winningNumbers.intersect(yourNumbers).size - 1 ).toLong()
        }.sum()
    }

    fun part2(input: List<String>): Long {
        val scores = input.map { game ->
            var inWinning = false
            var inPlayers = false
            val winningNumbers = emptySet<Long>().toMutableSet()
            val yourNumbers = emptySet<Long>().toMutableSet()
            var currentNumber = ""
            game.toCharArray().map {
                when {
                    it.isDigit() -> {
                        currentNumber = if (currentNumber.isNotEmpty()) {
                            "$currentNumber$it"
                        } else {
                            it.toString()
                        }
                    }
                    it.isWhitespace() -> {
                        if (currentNumber.isNotEmpty()) {
                            if (inWinning) {
                                winningNumbers.add(currentNumber.toLong())
                            }
                            if (inPlayers) {
                                yourNumbers.add(currentNumber.toLong())
                            }
                            currentNumber = ""
                        }
                    }
                    it == ':' -> {
                        inWinning = true
                        currentNumber = ""
                    }
                    it == '|' -> {inPlayers = true; inWinning=false}
                }
            }
            if (currentNumber.isNotEmpty()) {
                yourNumbers.add(currentNumber.toLong())
            }
            return@map winningNumbers.intersect(yourNumbers).size
        }
        val numberOfCards = MutableList(scores.size) { 1L }
        scores.forEachIndexed { index, score ->
            for (i in (index+1)..(index+score)) {
                numberOfCards[i] = numberOfCards[i] + numberOfCards[index]
            }
        }
        println(numberOfCards)
        return numberOfCards.sum().also { println(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.readInput("Day04_test")
    check(part1(testInput) == 13L)
    check(part2(testInput) == 30L)

    val input = Input.readInput("Day04")
    println("Part1:")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println("Part2:")
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
