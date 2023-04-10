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
    var check=0
    val binding by lazy {ActivityMembershipBinding.inflate(layoutInflater)  }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.ivBefore.setOnClickListener { finish() }

        binding.btnJoin.setOnClickListener {
            if (binding.etId.text.toString()=="" || binding.etPw.text.toString()=="" || binding.etPw2.text.toString()==""){
                Toast.makeText(this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show() }
            else if(check==0){
                Toast.makeText(this, "ID중복확인을 해주세요", Toast.LENGTH_SHORT).show()
            }else if (binding.etPw.text.toString()!=binding.etPw2.text.toString()){
                Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            }else{
                var fire=FirebaseFirestore.getInstance()
                var ref=fire.collection("userInfo")
                var info:MutableMap<String,String> = mutableMapOf()
                info.put("id",binding.etId.text.toString())
                info.put("pw",binding.etPw2.text.toString())
                info.put("lv",""+0)
                info.put("grade","")
                ref.document(binding.etId.text.toString()).set(info)
                Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                var intent=Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()

            }
        }


        binding.btnCheck.setOnClickListener {
            var fire=FirebaseFirestore.getInstance()
            fire.collection("userInfo").whereEqualTo("id",binding.etId.text.toString()).get().addOnSuccessListener {
                if(it.documents.size==0){
                    Toast.makeText(this, "사용가능한 ID입니다", Toast.LENGTH_SHORT).show()
                    check=1
                }else{
                    Toast.makeText(this, "중복된 ID입니다", Toast.LENGTH_SHORT).show()
                }
            }
        } //btncheck


    }
}