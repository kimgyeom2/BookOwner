package com.gy25m.bookowner.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gy25m.bookowner.R
import com.gy25m.bookowner.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivBefore.setOnClickListener{finish()}
    }
}