import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {
    fun part1(input: List<String>): Long {
        return input
            .asSequence()
            .map { line -> line.take((line.length / 2)) to line.takeLast(line.length / 2) }
            .map { line -> line.first.toSet().intersect(line.second.toSet()) }
            .map { intersects -> intersects.toList() }
            .flatten()
            .map { c: Char ->
                if (c.isLowerCase()) c.code.toLong().minus(96)
                else c.code.toLong().minus(38)
            }
            .sum()
    }

    fun part2(input: List<String>): Long {
        return input
            .asSequence()
            .map { it.toSet() }
            .windowed(size = 3, step = 3)
            .map { sets: List<Set<Char>> -> sets.reduce { acc, chars -> acc.intersect(chars) } }
            .flatten()
            .map { c: Char ->
                if (c.isLowerCase()) c.code.toLong().minus(96)
                else c.code.toLong().minus(38)
            }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157L)
    check(part2(testInput) == 70L)

    val input = readInput("Day03")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
