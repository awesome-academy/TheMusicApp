package com.hungngo.themusicapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivity.getIntent(this).also {
            startActivity(it)
        }
        finish()
    }
}
