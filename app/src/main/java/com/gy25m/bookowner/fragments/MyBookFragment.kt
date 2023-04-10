package com.gy25m.bookowner.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.telephony.TelephonyManager.ModemErrorException
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.gy25m.bookowner.G
import com.gy25m.bookowner.R
import com.gy25m.bookowner.adapters.MyBookAdapter
import com.gy25m.bookowner.databinding.DialogAddBookBinding
import com.gy25m.bookowner.databinding.FragmentMybookBinding
import com.gy25m.bookowner.model.MyBookItem
import org.checkerframework.checker.units.qual.A
import java.util.prefs.Preferences

class MyBookFragment : Fragment() {
    private var firestore=FirebaseFirestore.getInstance()
    private var reviewRef:CollectionReference=firestore.collection("${G.userId}")
    private lateinit var binding: FragmentMybookBinding
    var reviews:MutableMap<String,String> = mutableMapOf()
    var list: MutableList<MyBookItem> = mutableListOf()
    lateinit var adapter:MyBookAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMybookBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding.tvId.text=G.userId
        dataLoad()



        var infoDia=AlertDialog.Builder(requireContext()).setView(layoutInflater.inflate(R.layout.dialog_info,null)).create()
        binding.btnInfo.setOnClickListener{
            infoDia.show()
        }


        var dialogBinding=DialogAddBookBinding.inflate(layoutInflater)
        val resultLauncher :ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),object :ActivityResultCallback<ActivityResult>{
            override fun onActivityResult(result: ActivityResult?) {
                if (result?.resultCode == Activity.RESULT_CANCELED) return
                G.imgUri= result?.data?.data!!
                Glide.with(this@MyBookFragment).load(G.imgUri).into(dialogBinding.ivMyImg)
            }
        })

        var dia=AlertDialog.Builder(requireContext()).setView(dialogBinding.root).create()
        binding.addBook.setOnClickListener{

            dia.show()
            dialogBinding.ivMyImg.setOnClickListener {
                val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
                resultLauncher.launch(intent)
            }

            dialogBinding.btnConfirm.setOnClickListener {
                G.title=dialogBinding.etTitle.text.toString()
                G.review=dialogBinding.etReviewreal.text.toString()
                if (G.imgUri !=null && dialogBinding.etTitle.text.toString()!="" && dialogBinding.etReviewreal.text.toString()!="" ){
                    list.add(MyBookItem(G.imgUri!!,G.title!!,G.review!!))
                    adapter.notifyItemInserted(list.size)
                    binding.recyclerMybook.scrollToPosition(0)

                    dialogBinding.etTitle.text=null
                    dialogBinding.etReviewreal.text=null
                    Glide.with(requireContext()).load(R.drawable.icon_add).into(dialogBinding.ivMyImg)
                    lv()
                    dataSave()
                    dia.dismiss()
                }
                else{
                    Toast.makeText(requireContext(), "항목을 모두 채워주세요", Toast.LENGTH_SHORT).show()
                }
            }
            dialogBinding.btnCancel.setOnClickListener { dia.dismiss() }
        }
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    fun lv(){
        binding.tvLevel.text=list.size.toString()
        grade()
    }



    fun dataSave(){

        var docpath="MSG_"+System.currentTimeMillis()
        reviews.put("cover",G.imgUri!!.toString())
        reviews.put("title",G.title!!)
        reviews.put("text",G.review!!)
        reviews.put("docpath",docpath)
        reviewRef.document(docpath).set(reviews)
        binding.recyclerMybook.scrollToPosition(list.size-1)

        firestore.collection("userInfo").document("${G.userId}").update("lv",list.size)
        firestore.collection("userInfo").document("${G.userId}").update("grade",binding.tvGrade.text)

    }

    fun dataLoad(){
        var firestore=FirebaseFirestore.getInstance()
        var reviewRef=firestore.collection("${G.userId}")
        reviewRef.get().addOnSuccessListener {
            for (snapshot in it){
                var reviews:MutableMap<String,Any> = snapshot.data
                var cover=reviews.get("cover").toString()
                var title=reviews.get("title").toString()
                var text=reviews.get("text").toString()
                list.add(MyBookItem(Uri.parse(cover),title!!,text!!))
                binding.tvLevel.text=list.size.toString()
                grade()
            }
            adapter=MyBookAdapter(requireActivity(),list,binding)
            binding.recyclerMybook.adapter=adapter
            binding.recyclerMybook.scrollToPosition(list.size-1)
        }
    }
    fun grade() {
        if (list.size in 0 until 4) {
            binding.tvGrade.setTextColor(Color.parseColor("#9E6613"))
            binding.tvGrade.text = "Bronze"
        }else if (list.size in 4 until 8) {
            binding.tvGrade.setTextColor(Color.parseColor("#ACABAB"))
            binding.tvGrade.text = "Silver"
        } else if (list.size in 8 until 13) {
            binding.tvGrade.setTextColor(Color.parseColor("#F4E23C"))
            binding.tvGrade.text = "Gold"
        } else if (list.size in 13 until 19) {
            binding.tvGrade.setTextColor(Color.parseColor("#13BCAC"))
            binding.tvGrade.text = "Platinum"
        } else if (list.size >= 30) {
            binding.tvGrade.setTextColor(Color.parseColor("#53B5E1"))
            binding.tvGrade.text = "Diamond"
        }
    }
}