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
            val intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)}
        binding.btnJoin.setOnClickListener {
            val intent=Intent(this, MembershipActivity::class.java)
            startActivity(intent)}
        binding.gomain.setOnClickListener{
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)}
    }
}
