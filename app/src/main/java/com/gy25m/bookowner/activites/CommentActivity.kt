package com.gy25m.bookowner.activites

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.gy25m.bookowner.G
import com.gy25m.bookowner.adapters.CommentAdapter
import com.gy25m.bookowner.databinding.ActivityCommentBinding
import com.gy25m.bookowner.model.CommentItem

class CommentActivity : AppCompatActivity() {
    val binding by lazy { ActivityCommentBinding.inflate(layoutInflater) }
    var fire=FirebaseFirestore.getInstance()
    var comRef=fire.collection("commentName")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var replys= mutableListOf<CommentItem>()
        binding.commentId.text=intent.getStringExtra("id")
        binding.commentText.text=intent.getStringExtra("text")

        Glide.with(this).load(intent.getStringExtra("img")).into(binding.civ)
        binding.btnBack.setOnClickListener { finish() }

        binding.btnSend.setOnClickListener {
            replys.add(CommentItem(G.userId.toString(),binding.etComment.text.toString()))
            var adapter=CommentAdapter(this,replys)
            binding.recyclerComments.adapter=adapter
            adapter.notifyItemInserted(replys.size)

            // 소프트키보드 숨기기
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)


            var map= mutableMapOf<String,String>()
            map.put("id",G.userId.toString())
            map.put("text",binding.etComment.text.toString())
            comRef.document("reply_${System.currentTimeMillis()}").set(map)
            binding.etComment.setText("")
        }

    }


}