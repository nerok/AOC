data class Point<T>(var content: T, val row: Int, val column: Int) {
    override fun toString(): String {
        return content.toString()
    }
}

open class GenericGrid<T : Any>(var rows: MutableList<MutableList<Point<T>>> = emptyList<MutableList<Point<T>>>().toMutableList(), val defaultValue: T) {

    private var zeroRow = 0
    private var zeroColumn = 0

    val width: Int by lazy {
        rows.firstOrNull()?.size ?: 0
    }
    val height: Int by lazy {
        rows.size
    }
    val shape: Pair<Int, Int> by lazy {
        height to width
    }

    private fun merge(grid: GenericGrid<T>): GenericGrid<T> {
        this.rows.mapIndexed { rowIndex, columns ->
            columns.mapIndexed { colIndex, point ->
                if (point.content == -1 && grid[rowIndex, colIndex].content != -1) {
                    point.content = grid[rowIndex, colIndex].content
                }
            }
        }
        return this
    }

    open fun copy(): GenericGrid<T> {
        return GenericGrid(
            rows = rows
                .map { cols ->
                    cols.map { point ->
                        point.copy()
                    }.toMutableList()
                }
                .toMutableList(),
            defaultValue
        )
    }

    fun rowReversed(): GenericGrid<T> {
        val rowReversed = this.copy()
        rowReversed.rows.reverse()
        return rowReversed
    }

    fun transpose(): GenericGrid<T> {
        val transposed = this.copy()
        this.rows.forEachIndexed { rowIndex, cols ->
            cols.forEachIndexed { colsIndex, point ->
                transposed[colsIndex, rowIndex] = point.copy()
            }
        }
        return transposed
    }

    operator fun set(row: Int, column: Int, value: Point<T>) {
        while (row - zeroRow < 0) {
            rows.prepend(
                (-zeroColumn..(width - zeroColumn))
                    .map { i ->
                        Point(defaultValue, (row - zeroRow), i)
                    }.toMutableList()
            )
            zeroRow++
        }
        while (row - zeroRow >= rows.size) {
            rows.append(
                (-zeroColumn..(width - zeroColumn))
                    .map { i ->
                        Point(defaultValue, (row - zeroRow), i)
                    }.toMutableList()
            )
        }
        rows[row][column] = value
    }

    operator fun get(row: Int, column: Int): Point<T> {
        return rows[row][column]
    }

    override fun toString(): String {
        return rows.joinToString("\n") { points -> points.joinToString("\t") { it.toString() } }
    }
}

enum class Direction {
    NORTH, EAST, SOUTH, WEST
}
