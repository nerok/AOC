
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {
    fun part1(input: List<String>): Long {
        val heightMap = GenericGrid(defaultValue = '.' to -1)
        var tmpStartPoint: Point<Pair<Char, Int>>? = null
        var tmpEndPoint: Point<Pair<Char, Int>>? = null

        input
            .filter { it.isNotEmpty() }
            .forEachIndexed { rowIndex, line ->
                heightMap.rows.add(
                    line.mapIndexed { colIndex, s ->
                            val point = Point(s to -1, rowIndex, colIndex)
                            if (s == 'S') tmpStartPoint = point
                            if (s == 'E') tmpEndPoint = point
                            point
                        }.toMutableList()
                )
            }

        val startPoint = tmpStartPoint ?: throw NullPointerException("Start point not found")
        val endPoint = tmpEndPoint ?: throw NullPointerException("End point not found")

        heightMap[startPoint.row, startPoint.column].content = 'a' to -1
        heightMap[endPoint.row, endPoint.column].content = 'z' to 0

        var currentNode: Point<Pair<Char, Int>>? = endPoint
        var toCheck = mutableSetOf<Point<Pair<Char, Int>>>()

        while (currentNode != null) {
            heightMap
                .findLegalMoves(currentNode)
                .filter { it.content.second < 0 || it.content.second > currentNode!!.content.second + 1 }
                .map { neighbour ->
                    neighbour.content = neighbour.content.first to currentNode!!.content.second + 1
                    neighbour
                }
                .also { toCheck.addAll(it) }

            currentNode = toCheck.removeFirstOrNull()
        }

        return startPoint.content.second.toLong()
    }

    fun part2(input: List<String>): Long {
        val heightMap = GenericGrid(defaultValue = '.' to -1)
        var tmpStartPoint: Point<Pair<Char, Int>>? = null
        var tmpEndPoint: Point<Pair<Char, Int>>? = null

        input
            .filter { it.isNotEmpty() }
            .forEachIndexed { rowIndex, line ->
                heightMap.rows.add(
                    line.mapIndexed { colIndex, s ->
                        val point = Point(s to -1, rowIndex, colIndex)
                        if (s == 'S') tmpStartPoint = point
                        if (s == 'E') tmpEndPoint = point
                        point
                    }.toMutableList()
                )
            }

        val startPoint = tmpStartPoint ?: throw NullPointerException("Start point not found")
        val endPoint = tmpEndPoint ?: throw NullPointerException("End point not found")

        heightMap[startPoint.row, startPoint.column].content = 'a' to -1
        heightMap[endPoint.row, endPoint.column].content = 'z' to 0

        var currentNode: Point<Pair<Char, Int>>? = endPoint
        var toCheck = mutableSetOf<Point<Pair<Char, Int>>>()

        while (currentNode != null) {
            heightMap
                .findLegalMoves(currentNode!!)
                .filter { it.content.second < 0 || it.content.second > currentNode!!.content.second + 1 }
                .map { neighbour ->
                    neighbour.content = neighbour.content.first to currentNode!!.content.second + 1
                    neighbour
                }
                .also { toCheck.addAll(it) }

            currentNode = toCheck.removeFirstOrNull()
        }

        return heightMap.rows
            .flatMap { row ->
                row.filter { point ->
                    point.content.second > 0 &&
                    point.content.first == 'a'
                }
            }
            .minBy { it.content.second }.content.second.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31L)
    check(part2(testInput) == 29L)

    val input = readInput("Day12")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.MICROSECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.MICROSECONDS, 3))
}

private fun GenericGrid<Pair<Char, Int>>.findLegalMoves(currentPoint: Point<Pair<Char, Int>>): Set<Point<Pair<Char, Int>>> {
    val neighbours = this.orthogonalNeighbours(currentPoint)
    val legalMoves = mutableSetOf<Point<Pair<Char, Int>>>()
    neighbours.forEach { neighbour ->
        if (neighbour.content.first >= currentPoint.content.first - 1) {
            legalMoves.add(neighbour)
        }
    }
    return legalMoves
}
