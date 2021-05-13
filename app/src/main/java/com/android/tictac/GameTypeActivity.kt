package com.android.tictac

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game_type.*

class GameTypeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_type)
        btnSinglePlayer.setOnClickListener {
            MainActivity.start(this, MainActivity.SINGLE_PLAYER)
        }
        btnMultiPlayer.setOnClickListener {
            MainActivity.start(this, MainActivity.MULTI_PLAYER)
        }
    }
}