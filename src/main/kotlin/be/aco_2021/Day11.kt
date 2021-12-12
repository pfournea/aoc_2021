package be.aco_2021

import java.io.File

class Day11_1 {

    fun execute() {
        val matrix = File("src/main/resources/day11.txt")
            .readLines()
            .map { it.toCharArray().map { char -> char.toString().toInt() }.toMutableList() }
            .toMutableList()
        var flashes = 0
        repeat((1..100).count()) {
            flashes += executeStep(matrix)
        }
        println(flashes)
    }
}

class Day11_2 {

    fun execute() {
        val matrix = File("src/main/resources/day11.txt")
            .readLines()
            .map { it.toCharArray().map { char -> char.toString().toInt() }.toMutableList() }
            .toMutableList()
        var steps = 0
        do {
            executeStep(matrix)
            steps++
        } while (matrixHasAllZeroes(matrix).not())
        println(steps)
    }

    private fun matrixHasAllZeroes(matrix: MutableList<MutableList<Int>>): Boolean {
        return matrix.sumOf { row -> row.sum() } == 0
    }
}

fun executeStep(matrix: MutableList<MutableList<Int>>): Int {
    increaseWithOne(matrix)
    val flashMask = initializeFlashMask()
    val flashes = performFlashes(matrix, flashMask)
    setFlashedToZero(matrix)
//    printMatrix(matrix)
//    println()
//    println(flashes)
    return flashes
}

fun initializeFlashMask(): MutableList<MutableList<Boolean>> {
    val mask = MutableList(10) { mutableListOf<Boolean>() }
    for (row in mask.indices) {
        repeat(10) { number ->
            mask[row].add(number, false)
        }
    }
    return mask
}

fun setFlashedToZero(matrix: MutableList<MutableList<Int>>) {
    for (rowIndex in matrix.indices) {
        for (columnIndex in matrix.indices) {
            if (matrix[rowIndex][columnIndex] == 10) {
                matrix[rowIndex][columnIndex] = 0
            }
        }
    }
}

fun performFlashes(matrix: MutableList<MutableList<Int>>, flashMask: MutableList<MutableList<Boolean>>): Int {
    var flashesCounter = 0
    for (rowIndex in matrix.indices) {
        for (columnIndex in matrix.indices) {
            if (matrix[rowIndex][columnIndex] == 10 && flashMask[rowIndex][columnIndex].not()) {
                flashesCounter += flash(rowIndex, columnIndex, matrix, flashMask)
            }
        }
    }
    return flashesCounter
}

fun flash(
    rowIndex: Int,
    columnIndex: Int,
    matrix: MutableList<MutableList<Int>>,
    flashMask: MutableList<MutableList<Boolean>>
): Int {
    var counter = 1
    flashMask[rowIndex][columnIndex] = true
    getCoordinatesToIncreaseEnergy(rowIndex, columnIndex, matrix)
        .forEach {
            val value = matrix[it.row][it.column]
            if (value != 10) {
                matrix[it.row][it.column] += 1
                if (matrix[it.row][it.column] == 10) {
                    counter += flash(it.row, it.column, matrix, flashMask)
                }
            }
        }
    return counter
}

fun getCoordinatesToIncreaseEnergy(
    rowIndex: Int,
    columnIndex: Int,
    matrix: MutableList<MutableList<Int>>
): List<Coordinate> {
    val maxRowSizeIndex = matrix.size - 1
    val maxColumnSizeIndex = matrix[0].size - 1
    val minRowIndex = kotlin.math.max(0, rowIndex - 1)
    val maxRowIndex = kotlin.math.min(maxRowSizeIndex, rowIndex + 1)
    val minColumnIndex = kotlin.math.max(0, columnIndex - 1)
    val maxColumnIndex = kotlin.math.min(maxColumnSizeIndex, columnIndex + 1)

    val coordinates = mutableListOf<Coordinate>()

    for (row in (minRowIndex..maxRowIndex)) {
        for (column in (minColumnIndex..maxColumnIndex)) {
            if (row != rowIndex || column != columnIndex) {
                coordinates.add(Coordinate(row, column))
            }
        }
    }
    return coordinates.toList()
}

fun increaseWithOne(matrix: MutableList<MutableList<Int>>) {
    for ((rowIndex, row) in matrix.withIndex()) {
        for (columnIndex in row.indices) {
            matrix[rowIndex][columnIndex] += 1
        }
    }
}

fun printMatrix(matrix: MutableList<MutableList<Int>>) {
    for (row in matrix) {
        println(row)
    }
}

data class Coordinate(val row: Int, val column: Int)


fun main(args: Array<String>) {
    Day11_1().execute()
    Day11_2().execute()
}