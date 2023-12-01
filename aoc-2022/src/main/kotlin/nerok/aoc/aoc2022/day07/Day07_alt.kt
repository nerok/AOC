package nerok.aoc.aoc2022.day07

import nerok.aoc.utils.Input
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {

    fun part1(input: String): Long {
        val root = Directory("/", null, emptyList<FSNode>().toMutableList())
        var pwd = root
        input.lines().drop(1).forEach { line ->
            when {
                line == "$ cd /" -> {
                    pwd = root
                }
                line == "$ cd .." -> {
                    pwd = pwd.parent ?: root
                }
                line.startsWith("$ cd ") -> {
                    val name = line.removePrefix("$ cd ")
                    pwd = pwd.children.filterIsInstance<Directory>().find { it.name == name }!!
                }
                line.isEmpty() -> {}
                line == "$ ls" -> {}
                line.startsWith("dir ") -> {
                    pwd.children.add(Directory(line.removePrefix("dir "), pwd, emptyList<FSNode>().toMutableList()))
                }
                else -> pwd.children.add(Files(line.split(" ").last(), pwd, line.split(" ").first().toLong()))
            }
        }

        return input.length.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.getInput("Day07_test")
    check(part1(testInput) == 95437L)

    val input = Input.getInput("Day07")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
}

abstract class FSNode(
    val name: String,
    val parent: Directory?
) {
    abstract fun size(): Long
}
class Directory(
    name: String,
    parent: Directory?,
    val children: MutableList<FSNode>,
) : FSNode(name, parent) {

    override fun size(): Long {
        return children.sumOf { it.size() }
    }
}
class Files(
    name: String,
    parent: Directory,
    private var size: Long
) : FSNode(name, parent) {
    override fun size() = size
}
