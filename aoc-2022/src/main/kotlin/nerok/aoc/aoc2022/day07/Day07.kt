package nerok.aoc.aoc2022.day07
import nerok.aoc.utils.Input
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    fun treeWriter(tree: MutableMap<String, Any>, key: String, values: MutableMap<String, Any>, separator: Char = '/'): MutableMap<String, Any> {
        if (key == "/") return values
        val keyList = key.split(separator).filter { it.isNotEmpty() }
        var currentSubTree = tree
        keyList.dropLast(1).forEach { keyFragment ->
            currentSubTree = currentSubTree[keyFragment] as MutableMap<String, Any>
        }
        currentSubTree[keyList.last()] = values
        return tree
    }

    fun part1(input: String): Long {
        var pwd = ""
        var dirTree = emptyMap<String, Any>().toMutableMap()
        input
            .split("$")
            .filter { it.isNotEmpty() }
            .map { line ->
                line
                    .lines()
                    .filter { it.isNotEmpty() }
                    .map { it.trim() }
            }.forEach { command ->
                if (command.first().startsWith("cd ")) {
                    val path = command.first().removePrefix("cd ")
                    pwd = when (path) {
                        ".." -> {
                            pwd
                                .split("/")
                                .filter { it.isNotEmpty() }
                                .dropLast(1)
                                .joinToString("/", "/", "/")
                                .replace("//", "/")
                        }
                        "/" -> {
                            "/"
                        }
                        else -> {
                            "$pwd$path/"
                        }
                    }
                } else {
                    val output = command.drop(1)
                    var values = emptyMap<String, Any>().toMutableMap()
                    output.forEach { item ->
                        val (first, last) = item.split(" ")
                        if (first == "dir") {
                            values[last] = emptyMap<String, Any>().toMutableMap()
                        } else {
                            values[last] = first
                        }
                    }
                    dirTree = treeWriter(dirTree, pwd, values)
                }
            }
        val sizedTree = calculateSize(dirTree)
        val sizeLimit = 100000L
        return filterSubtreesBySize(sizedTree, sizeLimit).sumOf { it["_sum"] as Long }
    }

    fun part2(input: String): Long {
        val totalSpace = 70000000L
        val requiredFree = 30000000L

        var pwd = ""
        var dirTree = emptyMap<String, Any>().toMutableMap()
        input
            .split("$")
            .filter { it.isNotEmpty() }
            .map { line ->
                line
                    .lines()
                    .filter { it.isNotEmpty() }
                    .map { it.trim() }
            }.forEach { command ->
                if (command.first().startsWith("cd ")) {
                    val path = command.first().removePrefix("cd ")
                    pwd = when (path) {
                        ".." -> {
                            pwd
                                .split("/")
                                .filter { it.isNotEmpty() }
                                .dropLast(1)
                                .joinToString("/", "/", "/")
                                .replace("//", "/")
                        }
                        "/" -> {
                            "/"
                        }
                        else -> {
                            "$pwd$path/"
                        }
                    }
                } else {
                    val output = command.drop(1)
                    var values = emptyMap<String, Any>().toMutableMap()
                    output.forEach { item ->
                        val (first, last) = item.split(" ")
                        if (first == "dir") {
                            values[last] = emptyMap<String, Any>().toMutableMap()
                        } else {
                            values[last] = first
                        }
                    }
                    dirTree = treeWriter(dirTree, pwd, values)
                }
            }
        val sizedTree = calculateSize(dirTree)

        val freeSpace = totalSpace.minus(sizedTree["_sum"] as Long)
        val minimumSpaceToDelete = requiredFree.minus(freeSpace)

        return filterSubtreesBySize(sizedTree, minimumSpaceToDelete, false).map { it["_sum"] as Long }.min()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.getInput("Day07_test")
    check(part1(testInput) == 95437L)
    check(part2(testInput) == 24933642L)

    val input = Input.getInput("Day07")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}

private fun filterSubtreesBySize(tree: MutableMap<String, Any>, sizeLimit: Long, filterSmaller: Boolean = true): List<MutableMap<String, Any>> {
    var subtrees = emptyList<MutableMap<String, Any>>().toMutableList()
    tree.forEach { (_, value) ->
        if (value is MutableMap<*, *>) {
            subtrees = subtrees.plus(filterSubtreesBySize(value as MutableMap<String, Any>, sizeLimit, filterSmaller)).toMutableList()
        }
    }
    if (filterSmaller && tree["_sum"] as Long <= sizeLimit) subtrees.add(tree)
    if (!filterSmaller && tree["_sum"] as Long >= sizeLimit) subtrees.add(tree)
    return subtrees
}

private fun calculateSize(tree: MutableMap<String, Any>): MutableMap<String, Any> {
    var sum = 0L
    tree.forEach { (_, value) ->
        sum = if (value is MutableMap<*, *>) {
            val subtree = calculateSize(value as MutableMap<String, Any>)
            sum.plus(subtree["_sum"].toString().toLong())
        } else {
            value.toString().toLong().plus(sum)
        }
    }
    tree["_sum"] = sum
    return tree
}
