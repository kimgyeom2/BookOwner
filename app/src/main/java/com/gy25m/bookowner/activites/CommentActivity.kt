package com.gy25m.bookowner.activites

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.gy25m.bookowner.G
import com.gy25m.bookowner.adapters.CommentAdapter
import com.gy25m.bookowner.databinding.ActivityCommentBinding
import com.gy25m.bookowner.model.CommentItem
import org.checkerframework.checker.units.qual.A
import kotlin.random.Random

class CommentActivity : AppCompatActivity() {
    val binding by lazy { ActivityCommentBinding.inflate(layoutInflater) }
    var replys= mutableListOf<CommentItem>()
    lateinit var adapter:CommentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.commentId.text=intent.getStringExtra("id")
        binding.commentText.text=intent.getStringExtra("text")
        var tag=intent.getStringExtra("tag")
        Glide.with(this).load(intent.getStringExtra("img")).into(binding.civ)


        var fire=FirebaseFirestore.getInstance()
        var comRef=fire.collection("reply")

        comRef.whereEqualTo("tag",tag).get().addOnSuccessListener {
            for (snapshot in it){
                var map:MutableMap<String,Any> = snapshot.data
                var id=map.get("id")
                var text=map.get("text")
                replys.add(CommentItem(id.toString(),text.toString()))
            }
            adapter=CommentAdapter(this,replys)
            binding.recyclerComments.adapter=adapter
            binding.recyclerComments.scrollToPosition(replys.size-1)
        }



        binding.btnBack.setOnClickListener { finish() }
        binding.btnSend.setOnClickListener {
            var tag=intent.getStringExtra("tag")
            replys.add(CommentItem(G.userId.toString(),binding.etComment.text.toString()))

            adapter.notifyItemInserted(replys.size)
            binding.recyclerComments.scrollToPosition(replys.size-1)

            // 소프트키보드 숨기기
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

            // 파이어베이스 저장
            var map= mutableMapOf<String,String>()
            map.put("id",G.userId.toString())
            map.put("text",binding.etComment.text.toString())
            map.put("tag",tag.toString())
            comRef.document("reply_"+System.currentTimeMillis()).set(map)
            binding.etComment.setText("")
        }

    }



}