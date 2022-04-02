package com.jasontsh.interviewkickstart.livedatalistactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.common.collect.ImmutableList

/**
 * A fragment representing a list of Items.
 */
class BetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bet_list, container, false)

        val model: BetViewModel by activityViewModels()
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = BetRecyclerViewAdapter(checkNotNull(model.betList.value), object : BetListCallback {
                    override fun onCheckChanged(position: Int, checked: Boolean) {
                        model.betList.value = ImmutableList.copyOf(checkNotNull(model.betList.value)
                            .mapIndexed { index, bet ->
                                if (index == position) {
                                    if (checked) {
                                        BetContent.Bet(bet.name, bet = 1)
                                    } else {
                                        BetContent.Bet(bet.name, bet = 0)
                                    }
                                } else {
                                    bet
                                }
                            })
                        view.post {
                            updateItem(view, position)
                        }
                    }

                    override fun onAddBet(position: Int) {
                        if (checkNotNull(model.betList.value?.get(position)?.bet) >= 1) {
                            model.betList.value = ImmutableList.copyOf(checkNotNull(model.betList.value)
                                .mapIndexed { index, bet ->
                                    if (index == position) {
                                        BetContent.Bet(bet.name, bet.bet + 1)
                                    } else {
                                        bet
                                    }
                                })
                            view.post {
                                updateItem(view, position)
                            }
                        }
                    }
                })
            }
        }
        return view
    }

    private fun updateItem(rec: RecyclerView, position: Int) {
        val model: BetViewModel by activityViewModels()
        (rec.adapter as BetRecyclerViewAdapter).values = checkNotNull(model.betList.value)
        rec.adapter?.notifyItemChanged(position)
    }
}