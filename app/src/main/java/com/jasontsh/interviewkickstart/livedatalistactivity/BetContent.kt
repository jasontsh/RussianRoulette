package com.jasontsh.interviewkickstart.livedatalistactivity

import androidx.lifecycle.MutableLiveData
import com.google.common.collect.ImmutableList
import java.util.ArrayList
import java.util.HashMap

/**
 * Class holding the bet options
 */
object BetContent {

    val ITEMS: MutableLiveData<ImmutableList<Bet>> by lazy {
        val builder: ImmutableList.Builder<Bet> = ImmutableList.builder()
        builder.add(Bet("Red"), Bet("Black"), Bet("Even"), Bet("Odd"), Bet("0"), Bet("00"))
        for (i in 1..COUNT) {
            builder.add(createBet(i))
        }
        MutableLiveData(builder.build())
    }

    val REDS = intArrayOf(32, 19, 21, 25, 34, 27, 36, 30, 23, 5, 16, 1, 14, 9, 18, 7, 12, 3)

    val BLACKS = intArrayOf(15, 4, 2, 17, 6, 13, 11, 8, 10, 24, 33, 20, 31, 22, 29, 28, 35, 26)

    private const val COUNT = 36

    const val TOTAL_SLOTS = 38

    private fun createBet(position: Int): Bet {
        return Bet(position.toString())
    }

    data class Bet(val name: String, var bet: Int = 0) {
        override fun toString(): String = name
    }
}