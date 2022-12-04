fun main() {
    fun part1(input: List<String>): Long {
        return input
            .map { line ->
                line.split(',')
                    .map { range ->
                        range.split('-')
                            .map { endpoint -> endpoint.toLong() }
                    }
                    .map { longs ->
                        LongRange(longs.first(), longs.last())
                    }
            }.count { longRanges ->
                val intersect = longRanges.first().intersect(longRanges.last())
                longRanges.first().toSet() == intersect || longRanges.last().toSet() == intersect
            }.toLong()
    }

    fun part2(input: List<String>): Long {
        return input
            .map { line ->
                line.split(',')
                    .map { range ->
                        range.split('-')
                            .map { endpoint -> endpoint.toLong() }
                    }
                    .map { longs ->
                        LongRange(longs.first(), longs.last())
                    }
            }.count { longRanges ->
                longRanges.first().intersect(longRanges.last()).isNotEmpty()
            }.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2L)
    check(part2(testInput) == 4L)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
