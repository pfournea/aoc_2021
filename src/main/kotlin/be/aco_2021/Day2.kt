package be.aco_2021

import java.io.File

class Day2_1 {
    fun execute() {
        var depth = 0
        var horizontalPosition = 0
        File("src/main/resources/day2_1.txt")
            .readLines()
            .map { it.split(" ") }
            .map { it[0] to it[1].toInt() }
            .forEach { pair ->
                when (pair.first) {
                    "forward" -> horizontalPosition += pair.second
                    "down" -> depth += pair.second
                    "up" -> depth -= pair.second
                }
            }
        println(horizontalPosition * depth)
    }
}

class Day2_2 {
    fun execute() {
        var depth = 0
        var horizontalPosition = 0
        var aim = 0
        File("src/main/resources/day2_1.txt")
            .readLines()
            .map { it.split(" ") }
            .map { it[0] to it[1].toInt() }
            .forEach { pair ->
                when (pair.first) {
                    "forward" -> {
                        horizontalPosition += pair.second
                        depth += aim * pair.second
                    }
                    "down" -> aim += pair.second
                    "up" -> aim -= pair.second
                }
            }
        println(horizontalPosition * depth)
    }
}

fun main(args: Array<String>) {
    Day2_1().execute()
    Day2_2().execute()
}