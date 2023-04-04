package com.gy25m.bookowner.activites

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.gy25m.bookowner.G
import com.gy25m.bookowner.R
import com.gy25m.bookowner.databinding.ActivityMembershipBinding
import com.gy25m.bookowner.model.MyBookItem

class MembershipActivity : AppCompatActivity() {

    val binding by lazy {ActivityMembershipBinding.inflate(layoutInflater)  }
    var check=false
    override fun onCreate(savedInstanceState: Bundle?) {
        var firestore=FirebaseFirestore.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.ivBefore.setOnClickListener { finish() }

        binding.btnJoin.setOnClickListener {
            G.userId=binding.etId.text.toString()
            G.userPw=binding.etPw.text.toString()
            G.userPw2=binding.etPw2.text.toString()


            if (G.userId!!.length<10){
                if (G.userId!="" && G.userPw2!="" && G.userPw==G.userPw2 && check){
                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    var reviewRef: CollectionReference =firestore.collection("userInfo")
                    var infos:MutableMap<String,String> = mutableMapOf()
                    infos.put("id", G.userId!!)
                    infos.put("pw", G.userPw2!!)
                    reviewRef.document("MSG_"+System.currentTimeMillis()).set(infos)
                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this, "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "ID가 너무김", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCheck.setOnClickListener {
            var firestore=FirebaseFirestore.getInstance()
            var infos=firestore.collection("userInfo")
            infos.get().addOnSuccessListener {
                for (snapshot in it) {
                    var reviews: MutableMap<String, Any> = snapshot.data
                    if (reviews.get("id").toString() == binding.etId.text.toString()) {
                        Toast.makeText(this, "아이디 중복", Toast.LENGTH_SHORT).show()
                        return@addOnSuccessListener
                    } else {
                        Toast.makeText(this, "사용가능", Toast.LENGTH_SHORT).show()
                        check=true
                        return@addOnSuccessListener
                    }

                }
            }
        }
    }
}