package com.gy25m.bookowner.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gy25m.bookowner.R
import com.gy25m.bookowner.databinding.ActivityMembershipBinding

class MembershipActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding=ActivityMembershipBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.ivBefore.setOnClickListener { finish() }
    }
}