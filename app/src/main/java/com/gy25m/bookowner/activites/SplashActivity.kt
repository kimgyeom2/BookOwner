package com.gy25m.bookowner.activites

import android.content.Intent
import android.graphics.Paint.Join
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.gy25m.bookowner.R
import com.gy25m.bookowner.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        moveJoin()
    }

    fun moveJoin(){
        Handler(Looper.getMainLooper()).postDelayed(
            kotlinx.coroutines.Runnable {
            startActivity(Intent(this,JoinActivity::class.java))
            finish()
        },500)
    }
}