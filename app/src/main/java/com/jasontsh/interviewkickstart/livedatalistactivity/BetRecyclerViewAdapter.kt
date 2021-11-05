package com.jasontsh.interviewkickstart.livedatalistactivity

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.MutableLiveData

import com.jasontsh.interviewkickstart.livedatalistactivity.BetContent.Bet
import com.jasontsh.interviewkickstart.livedatalistactivity.databinding.FragmentBetBinding

/**
 * [RecyclerView.Adapter] that can display a [Bet].
 * TODO: Replace the implementation with code for your data type.
 */
class BetRecyclerViewAdapter(
    private val values: List<Bet>
) : RecyclerView.Adapter<BetRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentBetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                holder.addBet.isClickable = true
                item.bet = 1
            } else {
                holder.addBet.isClickable = false
                item.bet = 0
            }
            holder.totalBet.text = item.bet.toString()
        }
        holder.addBet.setOnClickListener {
            if (holder.checkBox.isChecked) {
                item.bet++
                holder.totalBet.text = item.bet.toString()
            }
        }
        holder.checkBox.text = item.name
        holder.totalBet.text = item.bet.toString()
        holder.checkBox.isChecked = item.bet > 0
        holder.addBet.isClickable = holder.checkBox.isChecked
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentBetBinding) : RecyclerView.ViewHolder(binding.root) {
        val checkBox: CheckBox = binding.checkbox
        val addBet: Button = binding.addBet
        val totalBet: TextView = binding.betValue

        override fun toString(): String {
            return super.toString() + " '" + checkBox.text + "'"
        }
    }

}