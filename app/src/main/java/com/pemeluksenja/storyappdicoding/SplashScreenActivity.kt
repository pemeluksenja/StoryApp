package com.pemeluksenja.storyappdicoding

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreenActivity : AppCompatActivity() {
    private val context = this@SplashScreenActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        val pref =
            context.getSharedPreferences(R.string.token_pref.toString(), Context.MODE_PRIVATE)
        val token = pref.getString(R.string.token.toString(), "")
        val handlerSplash = Handler(Looper.getMainLooper())
        handlerSplash.postDelayed({
            if (token == "") {
                val splashScreen = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                startActivity(splashScreen)
            } else {
                val splashScreen = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(splashScreen)
            }
            finish()
        }, timeInMilliSecond)
    }

    companion object {
        const val timeInMilliSecond = 1800L
    }
}