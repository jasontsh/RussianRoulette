package com.jasontsh.interviewkickstart.livedatalistactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BetViewModel : ViewModel() {
    val betList: MutableList<MutableLiveData<BetContent.Bet>> by lazy {
        BetContent.ITEMS
    }

    val totalMoney: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(STARTING_MONEY)
    }

    companion object {
        const val STARTING_MONEY = 100
    }
}