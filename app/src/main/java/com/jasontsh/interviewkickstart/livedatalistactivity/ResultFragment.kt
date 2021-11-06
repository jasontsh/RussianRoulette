package com.jasontsh.interviewkickstart.livedatalistactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jasontsh.interviewkickstart.livedatalistactivity.BetContent.BLACKS
import com.jasontsh.interviewkickstart.livedatalistactivity.BetContent.REDS
import com.jasontsh.interviewkickstart.livedatalistactivity.BetContent.TOTAL_SLOTS
import com.jasontsh.interviewkickstart.livedatalistactivity.BetViewModel.Companion.STARTING_MONEY
import kotlin.collections.ArrayList
import kotlin.math.ceil

class ResultFragment : Fragment() {

    private val rolls = ArrayList<RollResult>(HISTORY_COUNT + 1)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        val rollButton: Button = view.findViewById(R.id.roll_button)
        val moneyLeft: TextView = view.findViewById(R.id.money_left)
        val resultTextView: TextView = view.findViewById(R.id.result_textview)
        val model: BetViewModel by activityViewModels()
        rollButton.setOnClickListener {
            val roll = ceil(Math.random() * TOTAL_SLOTS).toInt()
            val rollResult = getRollResult(roll, model.betList)
            model.totalMoney.value = checkNotNull(model.totalMoney.value) + rollResult.result
            displayNewRoll(resultTextView, rollResult)
            moneyLeft.text = "Total money left: " + checkNotNull(model.totalMoney.value).toString()
        }
        moneyLeft.text = "Total money left: $STARTING_MONEY"
        val betCount: TextView= view.findViewById(R.id.bet_count)
        model.betList.forEach { addObserver(it, model.betList, betCount) }
        return view
    }

    private fun addObserver(
        bet: MutableLiveData<BetContent.Bet>,
        bets: List<MutableLiveData<BetContent.Bet>>,
        betCount: TextView
    ) {
        bet.observe(
            this,
            Observer { betCount.text = "Bet count = " + bets.sumOf { checkNotNull(it.value).bet } })
    }

    private fun getRollResult(roll: Int, bets: List<MutableLiveData<BetContent.Bet>>): RollResult {
        var result = 0
        for (bet in bets) {
            val betValue = checkNotNull(bet.value)
            result +=
                when (betValue.name) {
                    "Red" -> if (REDS.contains(roll)) {
                        betValue.bet * 2
                    } else {
                        -betValue.bet
                    }
                    "Black" -> if (BLACKS.contains(roll)) {
                        betValue.bet * 2
                    } else {
                        -betValue.bet
                    }
                    "Even" -> if (roll % 2 == 0) {
                        betValue.bet * 2
                    } else {
                        -betValue.bet
                    }
                    "Odd" -> if (roll % 2 == 1) {
                        betValue.bet * 2
                    } else {
                        -betValue.bet
                    }
                    "0" -> if (roll == 37) {
                        betValue.bet * 36
                    } else {
                        -betValue.bet
                    }
                    "00" -> if (roll == 38) {
                        betValue.bet * 36
                    } else {
                        -betValue.bet
                    }
                    else -> if (betValue.name == roll.toString()) {
                        betValue.bet * 36
                    } else {
                        -betValue.bet
                    }
                }
        }
        return RollResult(roll, result)
    }

    private fun displayNewRoll(resultTextView: TextView, rollResult: RollResult) {
        rolls.add(0, rollResult)
        if (rolls.size > HISTORY_COUNT) {
            rolls.removeAt(HISTORY_COUNT)
        }
        val stringBuilder = StringBuilder()
        for (roll in rolls) {
            stringBuilder.append(
                when (roll.roll) {
                    37 -> "0"
                    38 -> "00"
                    else -> roll.roll.toString()
                }
            ).append(',')
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndex)
        resultTextView.text = stringBuilder.toString()
    }

    companion object {
        private const val HISTORY_COUNT = 6
    }
}