fun main() {
    fun part1(input: List<String>): Long {
        var max = 0L
        var current = 0L
        for (item in input) {
            current = (
                item.toLongOrNull()?.plus(current) ?: if (current > max) {
                    max = current
                    0L
                } else 0L
                )
        }
        return max
    }

    fun part2(input: List<String>): Long {
        val loads = mutableListOf<Long>()
        var current = 0L
        for (item in input) {
            val readItem = item.toLongOrNull()
            if (readItem == null) {
                loads.add(current)
                current = 0L
            } else {
                current += readItem
            }
        }
        if (current != 0L) {
            loads.add(current)
        }
        return loads.sorted().takeLast(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000L)
    check(part2(testInput) == 45000L)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
