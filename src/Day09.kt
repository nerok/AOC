import java.util.Collections
import kotlin.math.abs
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {

    fun part1(input: List<String>): Long {
        var H = GeoPoint(0, 0)
        var T = GeoPoint(0, 0)
        val visited = emptySet<GeoPoint>().toMutableSet()

        input.forEach { line ->
            val (direction, distance) = line.split(' ', limit = 2)
            // println("Direction: $direction, distance: $distance")
            when (direction) {
                "R" -> (1L..distance.toLong()).forEach {
                    H.moveRight()
                    if (!T.isAdjacent(H)) {
                        T.moveTowards(H)
                    }
                    visited.add(T.copy())
                }
                "U" -> (1L..distance.toLong()).forEach {
                    H.moveUp()
                    if (!T.isAdjacent(H)) {
                        T.moveTowards(H)
                    }
                    visited.add(T.copy())
                }
                "L" -> (1L..distance.toLong()).forEach {
                    H.moveLeft()
                    if (!T.isAdjacent(H)) {
                        T.moveTowards(H)
                    }
                    visited.add(T.copy())
                }
                "D" -> (1L..distance.toLong()).forEach {
                    H.moveDown()
                    if (!T.isAdjacent(H)) {
                        T.moveTowards(H)
                    }
                    visited.add(T.copy())
                }
                else -> {}
            }
        }

        return visited.size.toLong()
    }

    fun part2(input: List<String>): Long {
        var tail = Collections.nCopies(10, GeoPoint(0, 0)).map { it.copy() }.toMutableList()
        val visited = emptySet<GeoPoint>().toMutableSet()

        input.forEach { line ->
            val (direction, distance) = line.split(' ', limit = 2)
            when (direction) {
                "R" -> (1L..distance.toLong()).forEach {
                    tail[0].moveRight()
                    tail.windowed(2).forEach { (tmpHead, tmpTail) ->
                        if (!tmpTail.isAdjacent(tmpHead)) {
                            tmpTail.moveTowards(tmpHead)
                        }
                    }
                    visited.add(tail.last().copy())
                }
                "U" -> (1L..distance.toLong()).forEach {
                    tail.first().moveUp()
                    tail.windowed(2).forEach { (tmpHead, tmpTail) ->
                        if (!tmpTail.isAdjacent(tmpHead)) {
                            tmpTail.moveTowards(tmpHead)
                        }
                    }
                    visited.add(tail.last().copy())
                }
                "L" -> (1L..distance.toLong()).forEach {
                    tail.first().moveLeft()
                    tail.windowed(2).forEach { (tmpHead, tmpTail) ->
                        if (!tmpTail.isAdjacent(tmpHead)) {
                            tmpTail.moveTowards(tmpHead)
                        }
                    }
                    visited.add(tail.last().copy())
                }
                "D" -> (1L..distance.toLong()).forEach {
                    tail.first().moveDown()
                    tail.windowed(2).forEach { (tmpHead, tmpTail) ->
                        if (!tmpTail.isAdjacent(tmpHead)) {
                            tmpTail.moveTowards(tmpHead)
                        }
                    }
                    visited.add(tail.last().copy())
                }
                else -> {}
            }
        }

        return visited.size.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 88L)
    check(part2(testInput) == 36L)

    val input = readInput("Day09")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}

data class GeoPoint(var row: Int, var column: Int) {

    fun distance(other: GeoPoint): Pair<Long, Long> {
        return (this.row - other.row).toLong() to (this.column - other.column).toLong()
    }
    fun normalDistance(other: GeoPoint): Long {
        return maxOf(abs(this.column - other.column), abs(this.row - other.row)).toLong()
    }

    fun isAdjacent(other: GeoPoint): Boolean {
        return this.normalDistance(other) < 2
    }
    fun moveUp() {
        row--
    }

    fun moveDown() {
        row++
    }
    fun moveRight() {
        column++
    }

    fun moveLeft() {
        column--
    }

    fun moveTowards(other: GeoPoint) {
        val (rowDistance, columnDistance) = distance(other)
        if (rowDistance < -1) {
            moveDown()
            if (columnDistance >= 1) {
                moveLeft()
            } else if (columnDistance <= -1) {
                moveRight()
            }
        } else if (rowDistance > 1) {
            moveUp()
            if (columnDistance >= 1) {
                moveLeft()
            } else if (columnDistance <= -1) {
                moveRight()
            }
        } else {
            if (columnDistance > 1) {
                moveLeft()
                if (rowDistance >= 1) {
                    moveUp()
                } else if (rowDistance <= -1) {
                    moveDown()
                }
            } else if (columnDistance < -1) {
                moveRight()
                if (rowDistance >= 1) {
                    moveUp()
                } else if (rowDistance <= -1) {
                    moveDown()
                }
            }
        }
    }
}
