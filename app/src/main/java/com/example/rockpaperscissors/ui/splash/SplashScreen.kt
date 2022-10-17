package com.example.rockpaperscissors.ui.splash

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.slider.IntroSlider
import com.example.rockpaperscissors.ui.main.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // TODO: Warning:(13, 7) The application should not provide its own launch screen

        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this, MainActivity::class.java)
            val intent = Intent(this, IntroSlider::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}