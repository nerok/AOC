package nerok.aoc.aoc2021.day03

import nerok.aoc.utils.Input
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    fun getMajority(input: List<List<Int>>, index: Int): Int {
        return input.map { it[index] }.let {
            if (it.sum()*2 == it.size) {
                1
            }
            else if (it.sum() == 2 && it.size == 3) {
                1
            }
            else if (it.sum() > it.size / 2) 1 else 0
        }
    }

    fun higherReduction(set: List<List<Int>>): List<Int> {
        var reductionSet = set
        var index = 1
        while (reductionSet.size > 1) {
            val majority = getMajority(reductionSet, index)
            reductionSet = reductionSet.filter { it[index] == majority }
            index += 1
        }
        return reductionSet.first()
    }

    fun smallerReduction(set: List<List<Int>>): List<Int> {
        var reductionSet = set
        var index = 1
        while (reductionSet.size > 1) {
            val majority = getMajority(reductionSet, index)
            reductionSet = reductionSet.filter { it[index] != majority }
            index += 1
        }
        return reductionSet.first()
    }

    fun part1(input: List<String>): Long {
        val countArray = IntArray(input.first().length) { 0 }
        val inverseMask = IntArray(input.first().length) { 1 }.reduce { acc, i -> (acc shl 1) + i }
        input
            .map { it
                .mapIndexed { index, value ->
                    if (value == '1') {
                        countArray[index] = (countArray[index] + 1)
                    }
                    else {
                        countArray[index] = (countArray[index] - 1)
                    }
                }
            }
        val gamma = countArray
            .map { if (it > 0) 1 else 0 }
            .reduce { acc, i -> (acc shl 1) + i }
        val epsilon = gamma.xor(inverseMask)

        return (gamma*epsilon).toLong()

    }

    fun part2(inputValues: List<String>): Long {
        val input = inputValues
            .map { line ->
                line.split("")
                    .filter {
                        it.isNotEmpty()
                    }
                    .map { it.toInt() }
            }
        val majority = getMajority(input, 0)
        val highset = input.filter { it[0] == majority }
        val lowset = input.filter { it[0] != majority }
        val oxygenRating = higherReduction(highset)
        val CO2Rating = smallerReduction(lowset)
        val oxygenRatingDecimal = Integer.parseInt(oxygenRating.joinToString(separator = "") { it.toString() }, 2)
        val CO2RatingDecimal = Integer.parseInt(CO2Rating.joinToString(separator = "") { it.toString() }, 2)
        println(oxygenRatingDecimal)
        println(CO2RatingDecimal)
        println("${oxygenRatingDecimal*CO2RatingDecimal}")
        return (oxygenRatingDecimal*CO2RatingDecimal).toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.readInput("Day03_test")
    check(part1(testInput) == 198L)
    check(part2(testInput) == 230L)

    val input = Input.readInput("Day03")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
