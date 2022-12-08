import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {

    fun part1(input: List<String>): Long {
        val grid = Grid()
        input
            .filter { it.isNotEmpty() }
            .forEachIndexed { rowIndex, line ->
                grid.rows.add(
                    line
                        .split("")
                        .filter { it.isNotBlank() }
                        .mapIndexed { colIndex, s ->
                            Point(s.toInt(), rowIndex, colIndex)
                        }.toMutableList()
                )
            }

        return grid.findVisible().count().toLong()
    }

    fun part2(input: List<String>): Long {
        val grid = Grid()
        input
            .filter { it.isNotEmpty() }
            .forEachIndexed { rowIndex, line ->
                grid.rows.add(
                    line
                        .split("")
                        .filter { it.isNotBlank() }
                        .mapIndexed { colIndex, s ->
                            Point(s.toInt(), rowIndex, colIndex)
                        }.toMutableList()
                )
            }

        return grid.calculateScenicScore().max().toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21L)
    check(part2(testInput) == 8L)

    val input = readInput("Day08")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}

data class Point<T>(var content: T, val row: Int, val column: Int) {
    override fun toString(): String {
        return content.toString()
    }
}

class Grid(var rows: MutableList<MutableList<Point<Int>>> = emptyList<MutableList<Point<Int>>>().toMutableList()) {
    fun findVisible(): Grid {
        return Direction
            .values()
            .map { direction -> this.skyline(direction) }
            .reduce { acc, grid -> acc.merge(grid) }
    }

    private fun merge(grid: Grid): Grid {
        this.rows.mapIndexed { rowIndex, columns ->
            columns.mapIndexed { colIndex, point ->
                if (point.content == -1 && grid[rowIndex, colIndex].content != -1) {
                    point.content = grid[rowIndex, colIndex].content
                }
            }
        }
        return this
    }

    fun skyline(direction: Direction): Grid {
        val tmpGrid = when (direction) {
            Direction.NORTH -> {
                this.copy()
            }
            Direction.EAST -> {
                this.transpose().rowReversed()
            }
            Direction.SOUTH -> {
                this.rowReversed()
            }
            Direction.WEST -> {
                this.transpose()
            }
        }
        val skyline = tmpGrid.copy()
        val maxRow = tmpGrid.rows.first().toMutableList()
        tmpGrid.rows
            .drop(1)
            .forEachIndexed { rowIndex, columns ->
                columns
                    .forEachIndexed { colIndex, point ->
                        if (maxRow[colIndex].content < point.content) {
                            maxRow[colIndex] = point.copy()
                        } else {
                            skyline[rowIndex + 1, colIndex].content = -1
                        }
                    }
            }
        return when (direction) {
            Direction.NORTH -> {
                skyline.copy()
            }
            Direction.EAST -> {
                skyline.rowReversed().transpose()
            }
            Direction.SOUTH -> {
                skyline.rowReversed()
            }
            Direction.WEST -> {
                skyline.transpose()
            }
        }
    }

    fun copy(): Grid {
        return Grid(
            rows = rows
                .map { cols ->
                    cols.map { point ->
                        point.copy()
                    }.toMutableList()
                }
                .toMutableList()
        )
    }

    fun rowReversed(): Grid {
        val rowReversed = this.copy()
        rowReversed.rows.reverse()
        return rowReversed
    }

    fun transpose(): Grid {
        val transposed = this.copy()
        this.rows.forEachIndexed { rowIndex, cols ->
            cols.forEachIndexed { colsIndex, point ->
                transposed[colsIndex, rowIndex] = point.copy()
            }
        }
        return transposed
    }

    private operator fun set(row: Int, column: Int, value: Point<Int>) {
        rows[row][column] = value
    }

    operator fun get(row: Int, column: Int): Point<Int> {
        return rows[row][column]
    }

    override fun toString(): String {
        return rows.joinToString("\n") { points -> points.joinToString("\t") { it.toString() } }
    }

    fun count(): Int {
        return rows.sumOf { columns -> columns.count { point -> point.content >= 0 } }
    }

    fun calculateScenicScore(): Grid {
        val scenicScoreGrid = this.copy()
        this.rows.forEachIndexed { rowIndex, columns ->
            columns.forEachIndexed { columnIndex, point ->
                var score = 1
                // South
                var i = 1
                if (rowIndex + i >= this.rows.size) {
                    i = 0
                } else while (rowIndex + i < this.rows.size - 1 && this[rowIndex + i, columnIndex].content < point.content) {
                    i++
                }
                score *= i
                // North
                i = 1
                if (rowIndex - i < 0) {
                    i = 0
                } else while (rowIndex - i > 0 && this[rowIndex - i, columnIndex].content < point.content) {
                    i++
                }
                score *= i
                // East
                i = 1
                if (columnIndex + i >= this.rows.first().size) {
                    i = 0
                } else while (columnIndex + i < this.rows.first().size - 1 && this[rowIndex, columnIndex + i].content < point.content) {
                    i++
                }
                score *= i
                // West
                i = 1
                if (columnIndex - i < 0) {
                    i = 0
                } else while (columnIndex - i > 0 && this[rowIndex, columnIndex - i].content < point.content) {
                    i++
                }
                score *= i
                scenicScoreGrid[rowIndex, columnIndex].content = score
            }
        }
        return scenicScoreGrid
    }

    fun max(): Int = this.rows.maxOf { col -> col.maxOf { it.content } }
}

enum class Direction {
    NORTH, EAST, SOUTH, WEST
}
