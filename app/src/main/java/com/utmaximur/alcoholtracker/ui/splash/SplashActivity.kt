package com.utmaximur.alcoholtracker.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.ui.main.MainActivity
import com.utmaximur.alcoholtracker.util.alphaView


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)
        findViewById<ImageView>(R.id.splash_image).alphaView(this)
        findViewById<TextView>(R.id.splash_text).alphaView(this)
        navigateToMainScreen()
    }

    private fun navigateToMainScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val nextScreenIntent = Intent(this, MainActivity::class.java)
            startActivity(nextScreenIntent)
            finish()
        }, 1000)
    }
}