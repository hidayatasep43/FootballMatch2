package com.hidayatasep.footballmatch.detailteam

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import app.data.Player
import com.hidayatasep.footballclub.GlideApp
import com.hidayatasep.footballmatch.R

/**
 * Created by hidayatasep43 on 10/9/2018.
 * hidayatasep43@gmail.com
 */
class PlayerAdapter (private val context: Context,
                     private val players: List<Player>,
                     private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_player, parent, false))
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val name = view.findViewById<TextView>(R.id.tv_name)
        private val position = view.findViewById<TextView>(R.id.tv_position)
        private val image = view.findViewById<ImageView>(R.id.image)

        fun bindItem(player: Player, listener: (Player) -> Unit) {
            name.text = player.playerName
            GlideApp.with(itemView.context)
                    .load(player.playerImage)
                    .into(image)
            position.text = player.playerPosition
            itemView.setOnClickListener {
                listener(player)
            }
        }

    }

}