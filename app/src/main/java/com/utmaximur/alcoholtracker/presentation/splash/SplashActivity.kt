package com.utmaximur.alcoholtracker.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.main.MainActivity
import com.utmaximur.alcoholtracker.presentation.splash.ui.theme.AlcoholTrackerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlcoholTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    SplashView()
                    lifecycleScope.launch {
                        delay(500)
                        val nextScreenIntent = Intent(baseContext, MainActivity::class.java)
                        startActivity(nextScreenIntent)
                        finish()
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun SplashView() {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(
            animationSpec = tween(durationMillis = 500)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launch_app),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 22.sp,
                color = MaterialTheme.colors.primary,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}