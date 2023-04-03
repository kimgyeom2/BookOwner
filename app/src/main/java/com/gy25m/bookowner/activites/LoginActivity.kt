package com.gy25m.bookowner.activites

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.gy25m.bookowner.G
import com.gy25m.bookowner.R
import com.gy25m.bookowner.databinding.ActivityLoginBinding
import com.gy25m.bookowner.model.MyBookItem

class LoginActivity : AppCompatActivity() {
    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var firestore= FirebaseFirestore.getInstance()
        var reviewRef=firestore.collection("userInfo")

        binding.ivBefore.setOnClickListener{finish()}
        binding.nomem.setOnClickListener{
            intent= Intent(this,MembershipActivity::class.java)
            startActivity(intent)}

            binding.btnLogin.setOnClickListener {
                var id=binding.etId.text.toString()
                var pw=binding.etPw.text.toString()
                if (id!="" && pw!=""){
                reviewRef.get().addOnSuccessListener {
                 for (snapshot in it) {
                        var infos: MutableMap<String, Any> = snapshot.data
                        if (infos.get("id").toString() == id) {
                            if(infos.get("pw").toString()==pw){
                                startActivity(Intent(this,MainActivity::class.java))
                                finish()
                                return@addOnSuccessListener
                            }else{
                                Toast.makeText(this, "비밀번호가 일치하지않습니다", Toast.LENGTH_SHORT).show()
                                return@addOnSuccessListener
                            }
                        }else{
                            Toast.makeText(this, "일치하는 아이디가 없습니다", Toast.LENGTH_SHORT).show()
                            return@addOnSuccessListener
                        }
                    }
                } 
              }else{
                    Toast.makeText(this, "모든항목을 채워주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
