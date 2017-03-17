package com.example.newsapp.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.example.newsapp.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(MainActivity.createIntent(this))
        finish()
    }
}
