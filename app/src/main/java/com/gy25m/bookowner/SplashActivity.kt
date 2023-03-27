package com.gy25m.bookowner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        moveJoin()
    }

    fun moveJoin(){
        Handler(Looper.getMainLooper()).postDelayed(
            kotlinx.coroutines.Runnable {
            val intent=Intent(this,JoinActivity::class.java)
            startActivity(intent)
            finish()
        },10)
    }
}