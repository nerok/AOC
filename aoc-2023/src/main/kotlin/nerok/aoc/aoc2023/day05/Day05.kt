package nerok.aoc.aoc2023.day05

import nerok.aoc.utils.Input
import nerok.aoc.utils.append
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    fun part1(input: String): Long {
        var seeds = emptyList<Long>().toMutableList()
        input.split("\n\n").forEach { block ->
            when {
                block.startsWith("seeds: ") -> {
                    seeds = block.split(": ").last().split(" ").map { it.toLong() }.toList().toMutableList()
                    //println("Seeds: $seeds")
                }
                block.startsWith("seed-to-soil map:") -> {
                    val tmpList = emptyList<Long>().toMutableList()
                    val removeSet = emptySet<Int>().toMutableSet()
                    block.split("\n").drop(1).forEach {line ->
                        val (dest, src, length) = line.split(" ").map { it.toLong() }
                        seeds.forEachIndexed { index, seed ->
                            if (seed in src..<(src+length)){
                                tmpList.add(dest+(seed-src))
                                removeSet.add(index)
                            }
                        }
                    }
                    removeSet.sortedDescending().forEach {
                        seeds.removeAt(it)
                    }
                    seeds.addAll(tmpList)
                    //println("Soil: $seeds")
                }
                block.startsWith("soil-to-fertilizer map:") -> {
                    val tmpList = emptyList<Long>().toMutableList()
                    val removeSet = emptySet<Int>().toMutableSet()
                    block.split("\n").drop(1).forEach {line ->
                        val (dest, src, length) = line.split(" ").map { it.toLong() }
                        seeds.forEachIndexed { index, seed ->
                            if (seed in src..<(src+length)){
                                tmpList.add(dest+(seed-src))
                                removeSet.add(index)
                            }
                        }
                    }
                    removeSet.sortedDescending().forEach {
                        seeds.removeAt(it)
                    }
                    seeds.addAll(tmpList)
                    //println("Fertilizer: $seeds")
                }
                block.startsWith("fertilizer-to-water map:") -> {
                    val tmpList = emptyList<Long>().toMutableList()
                    val removeSet = emptySet<Int>().toMutableSet()
                    block.split("\n").drop(1).forEach {line ->
                        val (dest, src, length) = line.split(" ").map { it.toLong() }
                        seeds.forEachIndexed { index, seed ->
                            if (seed in src..<(src+length)){
                                tmpList.add(dest+(seed-src))
                                removeSet.add(index)
                            }
                        }
                    }
                    removeSet.sortedDescending().forEach {
                        seeds.removeAt(it)
                    }
                    seeds.addAll(tmpList)
                    //println("Water: $seeds")
                }
                block.startsWith("water-to-light map:") -> {
                    val tmpList = emptyList<Long>().toMutableList()
                    val removeSet = emptySet<Int>().toMutableSet()
                    block.split("\n").drop(1).forEach {line ->
                        val (dest, src, length) = line.split(" ").map { it.toLong() }
                        seeds.forEachIndexed { index, seed ->
                            if (seed in src..<(src+length)){
                                tmpList.add(dest+(seed-src))
                                removeSet.add(index)
                            }
                        }
                    }
                    removeSet.sortedDescending().forEach {
                        seeds.removeAt(it)
                    }
                    seeds.addAll(tmpList)
                    //println("Light: $seeds")
                }
                block.startsWith("light-to-temperature map:") -> {
                    val tmpList = emptyList<Long>().toMutableList()
                    val removeSet = emptySet<Int>().toMutableSet()
                    block.split("\n").drop(1).forEach {line ->
                        val (dest, src, length) = line.split(" ").map { it.toLong() }
                        seeds.forEachIndexed { index, seed ->
                            if (seed in src..<(src+length)){
                                tmpList.add(dest+(seed-src))
                                removeSet.add(index)
                            }
                        }
                    }
                    removeSet.sortedDescending().forEach {
                        seeds.removeAt(it)
                    }
                    seeds.addAll(tmpList)
                    //println("Temperature: $seeds")
                }
                block.startsWith("temperature-to-humidity map:") -> {
                    val tmpList = emptyList<Long>().toMutableList()
                    val removeSet = emptySet<Int>().toMutableSet()
                    block.split("\n").drop(1).forEach {line ->
                        val (dest, src, length) = line.split(" ").map { it.toLong() }
                        seeds.forEachIndexed { index, seed ->
                            if (seed in src..<(src+length)){
                                tmpList.add(dest+(seed-src))
                                removeSet.add(index)
                            }
                        }
                    }
                    removeSet.sortedDescending().forEach {
                        seeds.removeAt(it)
                    }
                    seeds.addAll(tmpList)
                    //println("Humidity: $seeds")
                }
                block.startsWith("humidity-to-location map:") -> {
                    val tmpList = emptyList<Long>().toMutableList()
                    val removeSet = emptySet<Int>().toMutableSet()
                    block.split("\n").drop(1).filter { it.isNotEmpty() }.forEach {line ->
                        val (dest, src, length) = line.split(" ").map { it.toLong() }
                        seeds.forEachIndexed { index, seed ->
                            if (seed in src..<(src+length)){
                                tmpList.add(dest+(seed-src))
                                removeSet.add(index)
                            }
                        }
                    }
                    removeSet.sortedDescending().forEach {
                        seeds.removeAt(it)
                    }
                    seeds.addAll(tmpList)
                    //println("Location: $seeds")
                }
            }
        }
        return seeds.min()
    }

    fun Pair<Long,Long>.overlap(src: Long, size: Long): MutableList<Pair<Long, Long>> {
        val sizeValue = src+size-1
        val secondValue = this.first+this.second-1
        when {
            (secondValue<src) -> return emptyList<Pair<Long, Long>>().toMutableList()
            (this.first>sizeValue) -> return emptyList<Pair<Long, Long>>().toMutableList()
            (this.first<src) -> {
                if (secondValue<=sizeValue) return listOf((this.first to src-this.first), (src to (this.second-(src-this.first)))).toMutableList()
                return listOf((this.first to src-this.first), (sizeValue+1 to this.first+this.second-sizeValue+1), (src to size)).toMutableList()
            }
            (this.first==src) -> {
                if (secondValue<=sizeValue) return listOf((src to this.second)).toMutableList()
                return listOf((sizeValue+1 to secondValue-sizeValue), (src to size)).toMutableList()
            }
            (this.first>src) -> {
                if (secondValue<=sizeValue) return listOf(this).toMutableList()
                return listOf(sizeValue+1 to this.first+this.second-sizeValue-1, (this.first to (sizeValue-this.first)+1)).toMutableList()
            }
        }
        return emptyList<Pair<Long, Long>>().toMutableList()
    }

    fun iterate(block: String, seedList: List<Pair<Long,Long>>): List<Pair<Long,Long>> {
        val tmpList = emptyList<Pair<Long,Long>>().toMutableList()
        val seeds = seedList.toMutableList()
        block.split("\n").drop(1).filter{ it.isNotEmpty() }.forEach {line ->
            val (dest, src, length) = line.split(" ").map { it.toLong() }
            var i = 0
            while (i < seeds.size) {
                val seed = seeds[i]
                val overlapList = seed.overlap(src, length)
                if (overlapList.isEmpty()) {
                    ++i
                    continue
                }
                seeds.removeAt(i)
                // Move actually overlapping range
                val overlapping = overlapList.removeLast()
                tmpList.add(dest+(overlapping.first-src) to overlapping.second)
                overlapList.forEach { seeds.append(it) }
            }
        }
        seeds.addAll(tmpList)
        return seeds
    }

    fun part2(input: String): Long {
        var seeds = emptyList<Pair<Long,Long>>()
        input.split("\n\n").map { it.trim() }.forEach { block ->
            when {
                block.startsWith("seeds: ") -> {
                    seeds = block
                        .split(": ")
                        .last()
                        .split(" ")
                        .map { it.toLong() }
                        .windowed(size = 2, step = 2)
                        .map { it.first() to it.last() }
                    //println("Seeds: $seeds")
                }
                block.startsWith("seed-to-soil map:") -> {
                    seeds = iterate(block, seeds)
                    //println("Soil: $seeds")
                }
                block.startsWith("soil-to-fertilizer map:") -> {
                    seeds = iterate(block, seeds)
                    //println("Fertilizer: $seeds")
                }
                block.startsWith("fertilizer-to-water map:") -> {
                    seeds = iterate(block, seeds)
                    //println("Water: $seeds")
                }
                block.startsWith("water-to-light map:") -> {
                    seeds = iterate(block, seeds)
                    //println("Light: $seeds")
                }
                block.startsWith("light-to-temperature map:") -> {
                    seeds = iterate(block, seeds)
                    //println("Temperature: $seeds")
                }
                block.startsWith("temperature-to-humidity map:") -> {
                    seeds = iterate(block, seeds)
                    //println("Humidity: $seeds")
                }
                block.startsWith("humidity-to-location map:") -> {
                    seeds = iterate(block, seeds)
                    //println("Location: $seeds")
                }
            }
        }
        return seeds.minOf { it.first }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.getInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = Input.getInput("Day05")
    println("Part1:")
    println(measureTime { println(part1(input)) }.toString(DurationUnit.SECONDS, 3))
    println("Part2:")
    println(measureTime { println(part2(input)) }.toString(DurationUnit.SECONDS, 3))
}
