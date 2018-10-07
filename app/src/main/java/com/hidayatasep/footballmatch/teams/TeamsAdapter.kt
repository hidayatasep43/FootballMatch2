package com.hidayatasep.footballmatch.teams

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import app.data.Team
import com.hidayatasep.footballclub.GlideApp
import com.hidayatasep.footballmatch.R

/**
 * Created by hidayatasep43 on 10/7/2018.
 * hidayatasep43@gmail.com
 */
/**
 * Created by hidayatasep43 on 9/4/2018.
 * hidayatasep43@gmail.com
 */
class TeamsAdapter (private val context: Context,
                    private val items: List<Team>,
                    private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamsAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_team, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val name = view.findViewById<TextView>(R.id.name)
        private val image = view.findViewById<ImageView>(R.id.image)

        fun bindItem(team: Team, listener: (Team) -> Unit) {
            name.text = team.teamName
            GlideApp.with(itemView.context)
                    .load(team.teamBadge)
                    .into(image)
            itemView.setOnClickListener {
                listener(team)
            }
        }

    }

}