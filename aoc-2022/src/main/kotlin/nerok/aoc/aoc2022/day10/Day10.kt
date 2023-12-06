package nerok.aoc.aoc2022.day10
import nerok.aoc.utils.Input
import java.util.*
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {

    fun part1(input: List<String>): Long {
        var cycle = 0L
        var xValue = 1L
        var checkCycle = 20L
        val checkCyclePeriod = 40L
        val signalStrengths = emptyList<Long>().toMutableList()

        input.filter { it.isNotEmpty() }.forEach { line ->
            when {
                line.startsWith("noop") -> {
                    cycle++
                    if (cycle == checkCycle) {
                        signalStrengths.add(checkCycle * xValue)
                        checkCycle += checkCyclePeriod
                    }
                }
                line.startsWith("addx") -> {
                    cycle++
                    if (cycle == checkCycle) {
                        signalStrengths.add(checkCycle * xValue)
                        checkCycle += checkCyclePeriod
                    }
                    cycle++
                    if (cycle == checkCycle) {
                        signalStrengths.add(checkCycle * xValue)
                        checkCycle += checkCyclePeriod
                    }
                    xValue += line.split(" ").last().toInt()
                }
            }
        }

        return signalStrengths.sum()
    }

    fun part2(input: List<String>): String {
        var cycle = 0L
        var xValue = 1L
        val cyclePeriod = 40L
        val screen = listOf(Collections.nCopies(40, '.').toCharArray()).toMutableList()
        fun rowPosition() = (cycle / cyclePeriod).toInt()
        fun columnPosition() = (cycle % cyclePeriod).toInt()
        fun cursor() = 1L shl (cyclePeriod.toInt() - (columnPosition() - 2))
        fun sprite() = 0b111L shl ((cyclePeriod - (xValue - 1)).toInt())

        input.filter { it.isNotEmpty() }.forEach { line ->
            when {
                line.startsWith("noop") -> {
                    if (screen.size <= rowPosition()) screen.add(Collections.nCopies(40, '.').toCharArray())
                    screen[rowPosition()][columnPosition()] = if ((sprite() and cursor()) > 0) '#' else '.'
                    cycle++
                }
                line.startsWith("addx") -> {
                    if (screen.size <= rowPosition()) screen.add(Collections.nCopies(40, '.').toCharArray())
                    screen[rowPosition()][columnPosition()] = if ((sprite() and cursor()) > 0) '#' else '.'
                    cycle++

                    if (screen.size <= rowPosition()) screen.add(Collections.nCopies(40, '.').toCharArray())
                    screen[rowPosition()][columnPosition()] = if ((sprite() and cursor()) > 0) '#' else '.'
                    cycle++
                    xValue += line.split(" ").last().toInt()
                }
            }
        }

        return screen.joinToString("\n") { it.joinToString("") }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.readInput("Day10_test")
    check(part1(testInput) == 13140L)
    println(part2(testInput))

    val input = Input.readInput("Day10")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
