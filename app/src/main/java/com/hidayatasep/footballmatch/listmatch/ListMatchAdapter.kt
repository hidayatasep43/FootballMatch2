package com.hidayatasep.footballmatch.listmatch

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.data.Event
import app.helper.Utils
import com.hidayatasep.footballmatch.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list_match.view.*

/**
 * Created by hidayatasep43 on 9/16/2018.
 * hidayatasep43@gmail.com
 */
class ListMatchAdapter (private val context: Context,
                        private val events: List<Event>,
                        private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<ListMatchAdapter.ListMatchViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMatchViewHolder {
        return ListMatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_match, parent, false))
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: ListMatchViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

    class ListMatchViewHolder(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindItem(event: Event, listener: (Event) -> Unit) {
            itemView.tvHomeClub.text = event.homeTeam
            itemView.tvHomeScore.text = event.homeScore
            itemView.tvAwayClub.text = event.awayTeam
            itemView.tvAwayScore.text = event.awayScore
            itemView.tvTimeMatch.text = Utils.convertEventTimeToGMT(event.dateEvent, event.strTime)
            itemView.setOnClickListener {
                listener(event)
            }
        }

    }

}