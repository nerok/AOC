package nerok.aoc.aoc2021.day10

import nerok.aoc.utils.Input
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Long {
        var score = 0
        input.forEach{ line ->
            val stack = ArrayDeque<String>()
            var mismatchedBracket = ""
            line.split("").forEach { char ->
                if (mismatchedBracket != "") return@forEach
                when (char) {
                    "(" -> stack.add(char)
                    "[" -> stack.add(char)
                    "{" -> stack.add(char)
                    "<" -> stack.add(char)
                    ")" -> if (stack.last() == "(") stack.removeLast() else {
                        mismatchedBracket = char
                    }
                    "]" -> if (stack.last() == "[") stack.removeLast() else {
                        mismatchedBracket = char
                    }
                    "}" -> if (stack.last() == "{") stack.removeLast() else {
                        mismatchedBracket = char
                    }
                    ">" -> if (stack.last() == "<") stack.removeLast() else {
                        mismatchedBracket = char
                    }
                }
            }
            if (mismatchedBracket != "") {
                when (mismatchedBracket) {
                    ")" -> score += 3
                    "]" -> score += 57
                    "}" -> score += 1197
                    ">" -> score += 25137
                }
            }
        }
        return score.toLong()
    }

    fun part2(input: List<String>): Long {
        val scores = input.map { line ->
            var score = 0L
            val stack = ArrayDeque<String>()
            line.split("").forEach { char ->
                when (char) {
                    "(" -> stack.add(char)
                    "[" -> stack.add(char)
                    "{" -> stack.add(char)
                    "<" -> stack.add(char)
                    ")" -> if (stack.last() == "(") stack.removeLast() else {
                        return@map null
                    }
                    "]" -> if (stack.last() == "[") stack.removeLast() else {
                        return@map null
                    }
                    "}" -> if (stack.last() == "{") stack.removeLast() else {
                        return@map null
                    }
                    ">" -> if (stack.last() == "<") stack.removeLast() else {
                        return@map null
                    }
                }
            }
            while (stack.isNotEmpty()) {
                score *= 5
                when (stack.removeLast()) {
                    "(" -> score += 1
                    "[" -> score += 2
                    "{" -> score += 3
                    "<" -> score += 4
                }
            }
            score
        }.filterNotNull().sorted()
        return scores[scores.size/2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.readInput("Day10_test")
    check(part1(testInput) == 26397L)
    check(part2(testInput) == 288957L)

    val input = Input.readInput("Day10")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
