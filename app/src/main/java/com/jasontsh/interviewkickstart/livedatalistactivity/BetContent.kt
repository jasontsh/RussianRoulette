package com.jasontsh.interviewkickstart.livedatalistactivity

import java.util.ArrayList
import java.util.HashMap

/**
 * Class holding the bet options
 */
object BetContent {

    val ITEMS: MutableList<Bet> = ArrayList()

    val REDS = intArrayOf(32, 19, 21, 25, 34, 27, 36, 30, 23, 5, 16, 1, 14, 9, 18, 7, 12, 3)

    val BLACKS = intArrayOf(15, 4, 2, 17, 6, 13, 11, 8, 10, 24, 33, 20, 31, 22, 29, 28, 35, 26)

    private const val COUNT = 36

    const val TOTAL_SLOTS = 38

    init {
        addItem(Bet("Red"))
        addItem(Bet("Black"))
        addItem(Bet("Even"))
        addItem(Bet("Odd"))
        addItem(Bet("0"))
        addItem(Bet("00"))
        for (i in 1..COUNT) {
            addItem(createBet(i))
        }
    }

    private fun addItem(item: Bet) {
        ITEMS.add(item)
    }

    private fun createBet(position: Int): Bet {
        return Bet(position.toString())
    }

    data class Bet(val name: String, var bet: Int = 0) {
        override fun toString(): String = name
    }
}