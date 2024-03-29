package nerok.aoc.aoc2022.day05

import nerok.aoc.utils.Input
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    fun part1(input: String): String {
        val (mapInput, instructionInput) = input.split("\n\n")
        val parsedMap = mapInput
            .lines()
            .map { lines -> lines.chunked(4).map { point -> point.trim() } }

        val containerArray = Array(parsedMap.last().size) { emptyList<String>().toMutableList() }
        for (line in parsedMap) {
            if (line == parsedMap.last()) continue
            line.onEachIndexed { index, s ->
                if (s.isNotEmpty()) containerArray[index] =
                    listOf(s).plus(containerArray[index]).toMutableList()
            }
        }

        instructionInput
            .lines()
            .map { line -> line.split(" from ") }
            .filter { it.first().isNotEmpty() }
            .forEach { line ->
                val count = line.first().removePrefix("move ").toInt()
                val tmpSplit = line.last().split(" to ")
                val source = tmpSplit.first().toInt().minus(1)
                val destination = tmpSplit.last().toInt().minus(1)
                for (round in 1..count) {
                    val tmp = containerArray[source].removeLast()
                    containerArray[destination].add(tmp)
                }
            }

        return containerArray
            .map { it.last().removePrefix("[").removeSuffix("]") }
            .reduce { acc, s -> acc.plus(s) }
    }

    fun part2(input: String): String {
        val (mapInput, instructionInput) = input.split("\n\n")
        val parsedMap = mapInput
            .lines()
            .map { lines -> lines.chunked(4).map { point -> point.trim() } }

        val containerArray = Array(parsedMap.last().size) { emptyList<String>().toMutableList() }
        for (line in parsedMap) {
            if (line == parsedMap.last()) continue
            line.onEachIndexed { index, s ->
                if (s.isNotEmpty()) containerArray[index] =
                    listOf(s).plus(containerArray[index]).toMutableList()
            }
        }

        instructionInput
            .lines()
            .map { line -> line.split(" from ") }
            .filter { it.first().isNotEmpty() }
            .forEach { line ->
                val count = line.first().removePrefix("move ").toInt()
                val tmpSplit = line.last().split(" to ")
                val source = tmpSplit.first().toInt().minus(1)
                val destination = tmpSplit.last().toInt().minus(1)
                val tmp = emptyList<String>().toMutableList()
                for (round in 1..count) {
                    tmp.add(containerArray[source].removeLast())
                }
                tmp.reverse()
                containerArray[destination].addAll(tmp)
            }

        return containerArray
            .map { it.last().removePrefix("[").removeSuffix("]") }
            .reduce { acc, s -> acc.plus(s) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.getInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = Input.getInput("Day05")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
