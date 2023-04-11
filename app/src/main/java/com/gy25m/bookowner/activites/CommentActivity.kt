package com.gy25m.bookowner.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.gy25m.bookowner.R
import com.gy25m.bookowner.databinding.ActivityCommentBinding

class CommentActivity : AppCompatActivity() {
    val binding by lazy { ActivityCommentBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.commentId.text=intent.getStringExtra("id")
        binding.commentText.text=intent.getStringExtra("text")
        Glide.with(this).load(intent.getStringExtra("img")).into(binding.civ)
        binding.btnBack.setOnClickListener { finish() }

    }
}