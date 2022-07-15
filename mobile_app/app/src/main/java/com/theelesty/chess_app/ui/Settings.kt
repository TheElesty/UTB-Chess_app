package com.theelesty.chess_app.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.theelesty.chess_app.R
import com.theelesty.chess_app.data.UserDatastoreRepository
import com.theelesty.chess_app.databinding.ActivitySettingsBinding
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.coroutines.launch

private const val TAG = "Debug: Settings"

class Settings : AppCompatActivity() {
    private var _binding: ActivitySettingsBinding? = null
    private val binding get() = _binding!!


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.title_settings)

        UserDatastoreRepository.setAppContext(this)

        lifecycleScope.launch {
            val cLight = UserDatastoreRepository.read("lightColor")  ?: "#d38d5f"
            val cDark = UserDatastoreRepository.read("darkColor")  ?: "#784421"

            lightColor.setText(cLight)
            darkColor.setText(cDark)

            lightSquare.setBackgroundColor(Color.parseColor(lightColor.text.toString()))
            darkSquare.setBackgroundColor(Color.parseColor(darkColor.text.toString()))
        }

        lightColor.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                if(lightColor.text.toString() == "" ) lightColor.setText("#d38d5f")
                try {
                    lightSquare.setBackgroundColor(Color.parseColor(lightColor.text.toString()))
                } catch (iae: IllegalArgumentException) {
                    Toast.makeText(applicationContext,"Not a valid color",Toast.LENGTH_SHORT).show()
                    lightColor.setText("#d38d5f")
                }
            }
        }

        darkColor.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                if(darkColor.text.toString() == "" ) darkColor.setText("#784421")
                try {
                    darkSquare.setBackgroundColor(Color.parseColor(darkColor.text.toString()))
                } catch (iae: IllegalArgumentException) {
                    Toast.makeText(applicationContext,"Not a valid color",Toast.LENGTH_SHORT).show()
                    darkColor.setText("#784421")
                    darkSquare.setBackgroundColor(Color.parseColor(darkColor.text.toString()))
                }
            }
        }

        save.setOnClickListener{
            lifecycleScope.launch {
                UserDatastoreRepository.save("lightColor", lightColor.text.toString())
                UserDatastoreRepository.save("darkColor", darkColor.text.toString())
            }
        }

        reset.setOnClickListener{
            lightColor.setText("#d38d5f")
            darkColor.setText("#784421")

            lightSquare.setBackgroundColor(Color.parseColor(lightColor.text.toString()))
            darkSquare.setBackgroundColor(Color.parseColor(darkColor.text.toString()))

            lifecycleScope.launch {
                UserDatastoreRepository.save("lightColor", lightColor.text.toString())
                UserDatastoreRepository.save("darkColor", darkColor.text.toString())
            }
        }
    }
}