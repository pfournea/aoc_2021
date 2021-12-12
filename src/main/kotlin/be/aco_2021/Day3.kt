package be.aco_2021

import java.io.File

class Day3_1 {
    fun execute() {
        val listOfLists = File("src/main/resources/day3.txt")
            .readLines()
            .map { it.toList() }
        val result = mutableListOf<Char>()
        val indexLength = listOfLists[0].size
        for (i: Int in 0 until indexLength) {
            val countBits = countBits(i, listOfLists)
            val zeros = countBits.first
            val ones = countBits.second
            if (zeros > ones) {
                result.add('0')
            } else {
                result.add('1')
            }
        }
        val gammaRate = Integer.parseInt(String(result.toCharArray()), 2)
        val reversedResult = result.map {
            reverse(it)
        }
        val epsiloneRate = Integer.parseInt(String(reversedResult.toCharArray()), 2)
        println(gammaRate * epsiloneRate)
    }
}

class Day3_2 {
    fun execute() {
        val listOfLists = File("src/main/resources/day3.txt")
            .readLines()
            .map { it.toList() }
        var notFinished = true
        var currentIndex = 0
        val oxigenList = listOfLists.toMutableList()
        val co2List = listOfLists.toMutableList()
        while (notFinished) {
            if (oxigenList.size > 1) {
                val countBitsOxigen = countBits(currentIndex, oxigenList)
                val mostCommon = if (countBitsOxigen.first > countBitsOxigen.second) '0' else '1'
                oxigenList.removeAll { it[currentIndex] != mostCommon }
            }
            if (co2List.size > 1) {
                val countBitsCo2 = countBits(currentIndex, co2List)
                val leastCommon = if (countBitsCo2.first > countBitsCo2.second) '1' else '0'
                co2List.removeAll { it[currentIndex] != leastCommon }
            }
            currentIndex += 1
            notFinished = oxigenList.size > 1 || co2List.size > 1
        }
        val oxigenRate = Integer.parseInt(String(oxigenList[0].toCharArray()), 2)
        val co2Rate = Integer.parseInt(String(co2List[0].toCharArray()), 2)
        println(oxigenRate * co2Rate)
    }

}

fun countBits(currentIndex: Int, listOfCharLists: List<List<Char>>): Pair<Int, Int> {
    var zeroesCount = 0
    var onesCount = 0
    listOfCharLists.forEach {
        when (it[currentIndex]) {
            '0' -> zeroesCount++
            '1' -> onesCount++
        }
    }
    return zeroesCount to onesCount
}

fun reverse(c: Char): Char {
    return when (c) {
        '1' -> '0'
        '0' -> '1'
        else -> throw RuntimeException("unexpected")
    }
}

fun main(args: Array<String>) {
    Day3_1().execute()
    Day3_2().execute()
}