package be.aco_2021

import java.io.BufferedReader
import java.io.File
import java.util.regex.Pattern

val pattern = Pattern.compile("\\d+")

class Day4_1 {

    fun execute() {
        val bingoCards = mutableListOf<BingoCard>()
        val bufferedReader = File("src/main/resources/day4.txt").bufferedReader()
        val numberSequence = bufferedReader.readLine().split(",").map { Integer.valueOf(it) }
        bufferedReader.readLine()
        var bingoCard: BingoCard? = createBingoCard(bufferedReader)
        while (bingoCard != null) {
            bingoCards.add(bingoCard)
            bingoCard = createBingoCard(bufferedReader)
        }
        var bingoFound = false
        var numberSequenceIndex = 0
        while (!bingoFound) {
            val numberToMark = numberSequence[numberSequenceIndex++]
            bingoCards.forEach { card -> card.markNumberIfFoundAndCheckBingo(numberToMark) }
            val bingoCardWithBingo = bingoCards.filter { it.bingo }
            if (bingoCardWithBingo.isNotEmpty()) {
                println(bingoCardWithBingo[0].sumOfAllUnmarkedNumbers() * numberToMark)
                bingoFound = true
            }
        }
    }
}

class Day4_2 {

    fun execute() {
        val bingoCards = mutableListOf<BingoCard>()
        val bufferedReader = File("src/main/resources/day4.txt").bufferedReader()
        val numberSequence = bufferedReader.readLine().split(",").map { Integer.valueOf(it) }
        bufferedReader.readLine()
        var bingoCard: BingoCard? = createBingoCard(bufferedReader)
        while (bingoCard != null) {
            bingoCards.add(bingoCard)
            bingoCard = createBingoCard(bufferedReader)
        }
        var lastBingoCardWithBingoWithLastWinningNumber : Pair<BingoCard,Int>? = null
        for(seq in numberSequence) {
            bingoCards.forEach { card -> card.markNumberIfFoundAndCheckBingo(seq) }
            val bingoCardWithBingo = bingoCards.filter { it.bingo }
            if (bingoCardWithBingo.isNotEmpty()) {
                bingoCards.removeAll(bingoCardWithBingo)
                lastBingoCardWithBingoWithLastWinningNumber = Pair(bingoCardWithBingo[bingoCardWithBingo.size-1].makeCopy(),seq)
            }
        }
        println(lastBingoCardWithBingoWithLastWinningNumber!!.first.sumOfAllUnmarkedNumbers() * lastBingoCardWithBingoWithLastWinningNumber.second)
    }
}

fun main(args: Array<String>) {
    Day4_1().execute()
    Day4_2().execute()
}


private fun createBingoCard(bufferedReader: BufferedReader): BingoCard? {
    var line: String? = bufferedReader.readLine() ?: return null
    var rowNumber = 0
    val bingoNumbers = mutableListOf<BingoNumber>()
    while (line != null && line != "") {
        var columnNumber = 0
        val m = pattern.matcher(line)
        while (m.find()) {
            val bingoCardValue = Integer.valueOf(m.group())
            bingoNumbers.add(BingoNumber(rowNumber, columnNumber++, bingoCardValue))
        }
        line = bufferedReader.readLine()
        rowNumber += 1
    }
    return BingoCard(bingoNumbers)
}

data class BingoNumber(val row: Int, val column: Int, val value: Int, var marked: Boolean = false)

data class BingoCard(val bingoNumbers: List<BingoNumber>, var bingo: Boolean = false) {

    fun markNumberIfFoundAndCheckBingo(number: Int) {
        val bingoNumber = bingoNumbers.find { it.value == number }
        bingoNumber?.let {
            it.marked = true
            bingo = bingo || bingoNumbers.filter { bingoNumber -> bingoNumber.row == it.row }
                .all { bingoNumber -> bingoNumber.marked }
            bingo = bingo || bingoNumbers.filter { bingoNumber -> bingoNumber.column == it.column }
                .all { bingoNumber -> bingoNumber.marked }
        }
    }

    fun sumOfAllUnmarkedNumbers(): Int {
        return bingoNumbers.filter { it.marked.not() }.sumOf { it.value }
    }

    fun makeCopy(): BingoCard {
        return BingoCard(bingoNumbers = bingoNumbers.toMutableList().toList(), bingo = this.bingo)
    }

}