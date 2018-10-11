package com.hidayatasep.footballmatch.detailplayer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import app.data.Player
import app.helper.Utils
import com.hidayatasep.footballclub.GlideApp
import com.hidayatasep.footballmatch.R
import kotlinx.android.synthetic.main.activity_detail_player.*

class DetailPlayerActivity : AppCompatActivity() {

    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)

        player = intent.getParcelableExtra<Player>("player")
        supportActionBar?.title = player.playerName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        GlideApp.with(this)
                .load(player.playerImage)
                .into(imagePlayer)
        tvHeight.text = Utils.replaceMeterText(player.playerHeight)
        tvWeight.text = Utils.replaceKgText(player.playerWeight)
        tvPosition.text = player.playerPosition
        tvOverviewPlayer.text = player.playerDescription
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}
