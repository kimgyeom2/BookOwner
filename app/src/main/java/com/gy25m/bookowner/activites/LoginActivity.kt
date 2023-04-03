package com.gy25m.bookowner.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gy25m.bookowner.R
import com.gy25m.bookowner.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.ivBefore.setOnClickListener{finish()}
        binding.nomem.setOnClickListener{
            intent= Intent(this,MembershipActivity::class.java)
            startActivity(intent)
        }
    }
}