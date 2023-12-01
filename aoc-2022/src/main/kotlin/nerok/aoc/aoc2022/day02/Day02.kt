package nerok.aoc.aoc2022.day02

import nerok.aoc.utils.Input
import kotlin.time.DurationUnit
import kotlin.time.measureTime

enum class RockPaperScissor {
    ROCK, PAPER, SCISSOR
}

enum class Result {
    WIN, DRAW, LOSS
}

fun main() {
    val opponentAction = mapOf(
        "A" to RockPaperScissor.ROCK,
        "B" to RockPaperScissor.PAPER,
        "C" to RockPaperScissor.SCISSOR
    )

    val playerAction = mapOf(
        "X" to RockPaperScissor.ROCK,
        "Y" to RockPaperScissor.PAPER,
        "Z" to RockPaperScissor.SCISSOR
    )

    val actionScoring = mapOf(
        RockPaperScissor.ROCK to 1,
        RockPaperScissor.PAPER to 2,
        RockPaperScissor.SCISSOR to 3
    )

    val resultScoring = mapOf(
        -1L to 0L,
        0L to 3L,
        1L to 6L
    )

    val resultMap = mapOf(
        "X" to Result.LOSS,
        "Y" to Result.DRAW,
        "Z" to Result.WIN
    )

    val mappedResultScore = mapOf(
        Result.LOSS to 0L,
        Result.DRAW to 3L,
        Result.WIN to 6L
    )

    fun score(opponent: RockPaperScissor, player: RockPaperScissor): Long {
        if (opponent == player) return 0L
        if (opponent == RockPaperScissor.ROCK) {
            if (player == RockPaperScissor.PAPER) {
                return 1L
            }
            if (player == RockPaperScissor.SCISSOR) {
                return -1L
            }
            return 0L
        }
        if (opponent == RockPaperScissor.PAPER) {
            if (player == RockPaperScissor.SCISSOR) {
                return 1L
            }
            if (player == RockPaperScissor.ROCK) {
                return -1L
            }
            return 0L
        }
        if (opponent == RockPaperScissor.SCISSOR) {
            if (player == RockPaperScissor.ROCK) {
                return 1L
            }
            if (player == RockPaperScissor.PAPER) {
                return -1L
            }
            return 0L
        }
        return 0L
    }

    fun part1(input: List<String>): Long {
        var totalScore = 0L
        for (line in input) {
            val (opponent, player) = line.split(" ")
            val roundOpponentAction = opponentAction[opponent]
            val roundPlayerAction = playerAction[player]
            val result = score(roundOpponentAction!!, roundPlayerAction!!)
            totalScore += resultScoring[result]!! + actionScoring[roundPlayerAction]!!
        }
        return totalScore
    }

    fun findAction(roundOpponentAction: RockPaperScissor, result: Result): RockPaperScissor {
        when (result) {
            Result.DRAW -> return roundOpponentAction
            Result.WIN -> {
                if (roundOpponentAction == RockPaperScissor.ROCK) return RockPaperScissor.PAPER
                if (roundOpponentAction == RockPaperScissor.PAPER) return RockPaperScissor.SCISSOR
                return RockPaperScissor.ROCK
            }
            Result.LOSS -> {
                if (roundOpponentAction == RockPaperScissor.ROCK) return RockPaperScissor.SCISSOR
                if (roundOpponentAction == RockPaperScissor.PAPER) return RockPaperScissor.ROCK
                return RockPaperScissor.PAPER
            }
        }
    }

    fun part2(input: List<String>): Long {
        var totalScore = 0L
        for (line in input) {
            val (opponent, result) = line.split(" ")
            val roundOpponentAction = opponentAction[opponent]
            val parsedResult = resultMap[result]
            val identifiedPlayerAction = findAction(roundOpponentAction!!, parsedResult!!)
            totalScore += mappedResultScore[parsedResult]!! + actionScoring[identifiedPlayerAction]!!
        }
        return totalScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.readInput("Day02_test")
    check(part1(testInput) == 15L)
    check(part2(testInput) == 12L)

    val input = Input.readInput("Day02")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
