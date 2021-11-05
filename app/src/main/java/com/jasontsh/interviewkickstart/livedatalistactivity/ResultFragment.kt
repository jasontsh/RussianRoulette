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
import com.jasontsh.interviewkickstart.livedatalistactivity.BetContent.BLACKS
import com.jasontsh.interviewkickstart.livedatalistactivity.BetContent.REDS
import com.jasontsh.interviewkickstart.livedatalistactivity.BetContent.TOTAL_SLOTS
import com.jasontsh.interviewkickstart.livedatalistactivity.BetViewModel.Companion.STARTING_MONEY
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
        rollButton.setOnClickListener {
            val roll = ceil(Math.random() * TOTAL_SLOTS).toInt()
            val model: BetViewModel by activityViewModels()
            val rollResult = getRollResult(roll, checkNotNull(model.betList.value))
            model.totalMoney.value = checkNotNull(model.totalMoney.value) + rollResult.result
            displayNewRoll(resultTextView, rollResult)
            moneyLeft.text = "Total money left: " + checkNotNull(model.totalMoney.value).toString()
        }
        moneyLeft.text = "Total money left: $STARTING_MONEY"
        return view
    }

    private fun getRollResult(roll: Int, bets: List<BetContent.Bet>): RollResult {
        var result = 0
        for (bet in bets) {
            result +=
                when (bet.name) {
                    "Red" -> if (REDS.contains(roll)) {
                        bet.bet * 2
                    } else {
                        -bet.bet
                    }
                    "Black" -> if (BLACKS.contains(roll)) {
                        bet.bet * 2
                    } else {
                        -bet.bet
                    }
                    "Even" -> if (roll % 2 == 0) {
                        bet.bet * 2
                    } else {
                        -bet.bet
                    }
                    "Odd" -> if (roll % 2 == 1) {
                        bet.bet * 2
                    } else {
                        -bet.bet
                    }
                    "0" -> if (roll == 37) {
                        bet.bet * 36
                    } else {
                        -bet.bet
                    }
                    "00" -> if (roll == 38) {
                        bet.bet * 36
                    } else {
                        -bet.bet
                    }
                    else -> if (bet.name == roll.toString()) {
                        bet.bet * 36
                    } else {
                        -bet.bet
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
            stringBuilder.append(roll.roll).append(',')
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndex)
        resultTextView.text = stringBuilder.toString()
    }

    companion object {
        private const val HISTORY_COUNT = 6
    }
}