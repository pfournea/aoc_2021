package be.aco_2021

import java.io.File

class Day1_1 {
    fun execute() {
        println(File("src/main/resources/day1_1.txt")
            .readLines()
            .map { it.toInt() }
            .windowed(size = 2, step = 1)
            .count { it[1] > it[0] })
    }
}

class Day1_2 {
    fun execute() {
        println(File("src/main/resources/day1_1.txt")
            .readLines()
            .map { it.toInt() }
            .windowed(size = 3, step = 1)
            .map { it.sum() }
            .windowed(size = 2)
            .count { it[1] > it[0] })

    }
}

fun main(args: Array<String>) {
    Day1_1().execute()
    Day1_2().execute()
}