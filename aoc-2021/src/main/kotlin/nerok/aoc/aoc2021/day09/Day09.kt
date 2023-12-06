package nerok.aoc.aoc2021.day09

import nerok.aoc.utils.Input
import java.io.File
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {

    fun clusterBaisins(map: MutableList<List<Int>>, basinLimits: Set<Pair<Int,Int>>): Map<Int, Set<Pair<Int,Int>>> {
        var nextCluster = 0
        val clusterMap = mutableMapOf<Int, Set<Pair<Int,Int>>>()
        val clusterIndex = mutableMapOf<Pair<Int,Int>, Int>()
        map.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { index, point ->
                if (point == 9) return@forEachIndexed
                val columnNeighbours = when (rowIndex) {
                    0 -> {
                        listOf(rowIndex+1 to index to clusterIndex.getOrDefault(rowIndex+1 to index, -1))
                    }
                    map.size - 1 -> {
                        listOf(rowIndex-1 to index to clusterIndex.getOrDefault(rowIndex-1 to index, -1))
                    }
                    else -> {
                        listOf(
                            rowIndex-1 to index to clusterIndex.getOrDefault(rowIndex-1 to index, -1),
                            rowIndex+1 to index to clusterIndex.getOrDefault(rowIndex+1 to index, -1)
                        )
                    }
                }
                val rowNeighbours = when(index) {
                    0 -> {
                        listOf(rowIndex to index + 1 to clusterIndex.getOrDefault(rowIndex to index + 1, -1))
                    }
                    row.size - 1 -> {
                        listOf(rowIndex to index - 1 to clusterIndex.getOrDefault(rowIndex to index - 1, -1))
                    }
                    else -> {
                        listOf(
                            rowIndex to index-1 to clusterIndex.getOrDefault(rowIndex to index-1, -1),
                            rowIndex to index+1 to clusterIndex.getOrDefault(rowIndex to index+1, -1)
                        )
                    }
                }
                val neighbours = columnNeighbours + rowNeighbours
                if (neighbours.none { it.second != -1 }) {
                    val neighbourhood = (neighbours.map { it.first } + (rowIndex to index))
                        .filter { map[it.first][it.second] != 9 }
                        .toSet()
                    clusterMap[nextCluster] = neighbourhood
                    neighbourhood.forEach {
                        clusterIndex[it] = nextCluster
                    }
                    nextCluster = nextCluster.inc()
                }
                else {
                    val neighbourhood = (neighbours.map { it.first } + (rowIndex to index))
                        .filter { map[it.first][it.second] != 9 }
                        .toMutableSet()
                    var cluster = -1
                    neighbourhood.map {
                        if (cluster == -1) {
                            cluster = clusterIndex.getOrDefault(it, -1)
                        }
                        else {
                            val neighbourIndex = clusterIndex[it]
                            if (neighbourIndex != null && cluster != neighbourIndex) {
                                val newCluster = minOf(cluster, neighbourIndex)
                                val neighbourhood1 = clusterMap.getOrDefault(neighbourIndex, emptySet())
                                clusterMap.remove(neighbourIndex)
                                val neighbourhood2 = clusterMap.getOrDefault(cluster, emptySet())
                                clusterMap.remove(cluster)
                                val newNeighbourhood = neighbourhood1 + neighbourhood2
                                newNeighbourhood.forEach { clusterIndex[it] = newCluster }
                                clusterMap[newCluster] = newNeighbourhood
                            }
                        }
                    }
                    neighbourhood.forEach {
                        clusterIndex[it] = cluster
                    }
                    clusterMap[cluster]?.let { neighbourhood.addAll(it) }
                    clusterMap[cluster] = neighbourhood
                }
            }
        }
        return clusterMap
    }

    fun findBasinLimits(map: MutableList<List<Int>>): Set<Pair<Int,Int>> {
        val limits = mutableSetOf<Pair<Int,Int>>()
        map.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { index, point ->
                if (point == 9) limits.add(rowIndex to index)
            }
        }
        return limits
    }

    fun findLocalMinimas(map: MutableList<List<Int>>): List<Pair<Int,Int>> {
        val minimas = mutableListOf<Pair<Int,Int>>()
        map.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { index, point ->
                val possibleMinima = when (index) {
                    0 -> {
                        point < row[index+1]
                    }
                    row.size-1 -> {
                        point < row[index-1]
                    }
                    else -> {
                        (point < row[index-1]) && (point < row[index+1])
                    }
                }
                //println("Row $rowIndex, column $index: $possibleMinima")
                if (!possibleMinima) {
                    return@forEachIndexed
                }
                val localMinima = when (rowIndex) {
                    0 -> {
                        point < map[rowIndex+1][index]
                    }
                    map.size-1 -> {
                        point < map[rowIndex-1][index]
                    }
                    else -> {
                        (point < map[rowIndex-1][index]) && (point < map[rowIndex+1][index])
                    }
                }
                if (localMinima) {
                    minimas.add(rowIndex to index)
                }
            }
        }
        return minimas
    }

    fun part1(input: List<String>): Long {
        var coordinates = mutableListOf<List<Int>>()
        input.mapIndexed { index, line ->
            line.split("")
                .filterNot { it == "" }
                .map { it.toInt() }
                .let { coordinates.add(index, it) }
        }
        return findLocalMinimas(coordinates).sumOf { coordinates[it.first][it.second]+1 }.toLong()
    }

    fun part2(input: List<String>): Long {
        var coordinates = mutableListOf<List<Int>>()
        input.mapIndexed { index, line ->
            line.split("")
                .filterNot { it == "" }
                .map { it.toInt() }
                .let { coordinates.add(index, it) }
        }
        val limits = findBasinLimits(coordinates)
        return clusterBaisins(coordinates, limits)
            .map { it.value.size }
            .sorted()
            .takeLast(3)
            .reduce { acc: Int, i: Int ->
                acc * i
            }.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.readInput("Day09_test")
    check(part1(testInput) == 15L)
    check(part2(testInput) == 1134L)

    val input = Input.readInput("Day09")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
