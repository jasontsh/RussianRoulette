package com.jasontsh.interviewkickstart.livedatalistactivity

interface BetListCallback {
    fun onCheckChanged(position: Int, checked: Boolean) = Unit
    fun onAddBet(position: Int) = Unit
}