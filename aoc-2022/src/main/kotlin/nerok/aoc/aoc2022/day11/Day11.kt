package nerok.aoc.aoc2022.day11
import nerok.aoc.utils.Input
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {

    fun operateOnItem(item: ULong, operation: String): ULong {
        val (first, operator, last) = operation
            .trim()
            .removePrefix("new = ")
            .replace("old", item.toString())
            .split(" ")
        return when (operator) {
            "*" -> first.toULong() * last.toULong()
            "+" -> first.toULong() + last.toULong()
            else -> throw UnsupportedOperationException("Operator is not known: '$operator', first: $first, last: $last")
        }
    }

    fun runRoundP1(monkeys: List<Monkey>): List<Monkey> {
        monkeys.forEach { monkey: Monkey ->
            val monkeyItems = monkey.items
            monkey.items = emptyList<ULong>().toMutableList()
            monkeyItems.forEach { item ->
                val newScore = operateOnItem(item, monkey.operation) / 3uL
                if (newScore % monkey.test.toULong() == 0uL) {
                    monkeys[monkey.trueDest].items.add(newScore)
                } else {
                    monkeys[monkey.falseDest].items.add(newScore)
                }
                monkey.inspectedObjects++
            }
        }
        return monkeys
    }

    fun part1(input: String): ULong {
        var monkeys = input.split("\n\n").map { monkey ->
            val linedMonkey = monkey.lines()
            Monkey(
                id = linedMonkey.first().removePrefix("nerok.aoc.aoc2022.day11.Monkey ").removeSuffix(":").toULong(),
                items = linedMonkey[1].trim().removePrefix("Starting items: ").split(", ").map { it.toULong() }.toMutableList(),
                operation = linedMonkey[2].trim().removePrefix("Operation: "),
                test = linedMonkey[3].trim().removePrefix("Test: divisible by ").toInt(),
                trueDest = linedMonkey[4].trim().removePrefix("If true: throw to monkey ").toInt(),
                falseDest = linedMonkey[5].trim().removePrefix("If false: throw to monkey ").toInt()
            )
        }
        val rounds = 20
        (1..rounds).forEach { _ ->
            monkeys = runRoundP1(monkeys)
        }
        return monkeys.sortedBy { it.inspectedObjects }.takeLast(2).map { it.inspectedObjects }.reduce { acc, l -> acc * l }
    }

    fun runRoundP2(monkeys: List<Monkey>): List<Monkey> {
        val modulo = monkeys.map { it.test }.reduce { acc, i -> acc * i }.toULong()
        monkeys.forEach { monkey: Monkey ->
            val monkeyItems = monkey.items
            monkey.items = emptyList<ULong>().toMutableList()
            monkeyItems.forEach { item ->
                var newScore = operateOnItem(item, monkey.operation) % modulo
                // newScore = if (round % 2L == 0L) newScore  else newScore
                if (newScore % monkey.test.toULong() == 0uL) {
                    monkeys[monkey.trueDest].items.add(newScore)
                } else {
                    monkeys[monkey.falseDest].items.add(newScore)
                }
                monkey.inspectedObjects++
            }
        }
        return monkeys
    }

    fun part2(input: String): ULong {
        var monkeys = input.split("\n\n").map { monkey ->
            val linedMonkey = monkey.lines()
            Monkey(
                id = linedMonkey.first().removePrefix("nerok.aoc.aoc2022.day11.Monkey ").removeSuffix(":").toULong(),
                items = linedMonkey[1].trim().removePrefix("Starting items: ").split(", ").map { it.toULong() }.toMutableList(),
                operation = linedMonkey[2].trim().removePrefix("Operation: "),
                test = linedMonkey[3].trim().removePrefix("Test: divisible by ").toInt(),
                trueDest = linedMonkey[4].trim().removePrefix("If true: throw to monkey ").toInt(),
                falseDest = linedMonkey[5].trim().removePrefix("If false: throw to monkey ").toInt()
            )
        }
        val rounds = 10_000
        (1..rounds).forEach { _ ->
            monkeys = runRoundP2(monkeys)
        }
        return monkeys.sortedBy { it.inspectedObjects }.takeLast(2).map { it.inspectedObjects }.reduce { acc, l -> acc * l }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.getInput("Day11_test")
    check(part1(testInput) == 10605uL)
    check(part2(testInput) == 2713310158uL)

    val input = Input.getInput("Day11")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.MICROSECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.MICROSECONDS, 3))
}

data class Monkey(
    val id: ULong,
    var items: MutableList<ULong>,
    val operation: String,
    val test: Int,
    val falseDest: Int,
    val trueDest: Int,
    var inspectedObjects: ULong = 0uL
)
