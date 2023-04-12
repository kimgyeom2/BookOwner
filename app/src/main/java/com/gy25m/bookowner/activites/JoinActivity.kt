package com.gy25m.bookowner.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gy25m.bookowner.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {

    val binding by lazy {ActivityJoinBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnJoin.setOnClickListener {
            startActivity(Intent(this, MembershipActivity::class.java))
            }

    }
}
