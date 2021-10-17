package com.utmaximur.alcoholtracker.presentation.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.main.MainActivity
import com.utmaximur.alcoholtracker.util.alphaView
import com.utmaximur.alcoholtracker.util.delayOnLifeCycle


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)
        findViewById<ImageView>(R.id.splash_image).alphaView()
        findViewById<TextView>(R.id.splash_text).alphaView()
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        parent?.delayOnLifeCycle(1000) {
            val nextScreenIntent = Intent(this, MainActivity::class.java)
            startActivity(nextScreenIntent)
            finish()
        }
        return super.onCreateView(parent, name, context, attrs)
    }
}