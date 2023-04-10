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
        var fire=firestore.collection("userInfo")

        binding.ivBefore.setOnClickListener{finish()}
        binding.nomem.setOnClickListener{
            intent= Intent(this,MembershipActivity::class.java)
            startActivity(intent)
            finish()}

            binding.btnLogin.setOnClickListener {
                if (binding.etId.text.toString() == "" || binding.etPw.text.toString() == "") {
                    Toast.makeText(this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    fire.whereEqualTo("id", binding.etId.text.toString()).get()
                        .addOnSuccessListener {
                            if (it.documents.size == 0) Toast.makeText(
                                this,
                                "일치하는 ID가 없습니다",
                                Toast.LENGTH_SHORT
                            ).show()
                            else {
                                for (snapshot in it) {
                                    var aa: MutableMap<String, Any> = snapshot.data
                                    if (aa.get("pw").toString() == binding.etPw.text.toString()) {
                                        G.userId = binding.etId.text.toString()
                                        Toast.makeText(this, "로그인 성공+${G.userId}", Toast.LENGTH_SHORT).show()
                                        finishAffinity()
                                        startActivity(Intent(this, MainActivity::class.java))

                                    } else Toast.makeText(
                                        this,
                                        "비밀번호가 일치하지 않습니다",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                }
            }
        }
    }
