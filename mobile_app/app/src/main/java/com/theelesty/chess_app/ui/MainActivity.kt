package com.theelesty.chess_app.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.theelesty.chess_app.databinding.ActivityMainBinding
import com.theelesty.chess_app.ui.GamePlayer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        gamePlayer.setOnClickListener{
            val intent = Intent(this, GamePlayer::class.java)
            startActivity(intent)
        }
        gamePc.setOnClickListener{
            val intent = Intent(this, GameComputer::class.java)
            startActivity(intent)
        }
        dailyChallenge.setOnClickListener{
            val intent = Intent(this, DailyChallenge::class.java)
            startActivity(intent)
        }
        settings.setOnClickListener{
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }
    }
}