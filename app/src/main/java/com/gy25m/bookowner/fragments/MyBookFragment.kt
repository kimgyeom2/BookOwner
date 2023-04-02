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

    private lateinit var binding: FragmentMybookBinding
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


    @SuppressLint("ResourceAsColor")
    override fun onResume() {
        when(binding.tvLevel.text.toString().toInt()){
            in 4..10->{
                binding.tvGrade.setTextColor(R.color.silver) }
            in 11..16 ->{
                binding.tvGrade.setTextColor(R.color.gold) }
            in 17..24->{
                binding.tvGrade.setTextColor(R.color.platinum) }
            25->{
                binding.tvGrade.setTextColor(R.color.diamond) }
        }
        super.onResume()
    }
    override fun onPause() {
        var pref= context?.getSharedPreferences("userLv",0)
        var editor=pref?.edit()
        editor?.putString("lv",binding.tvLevel.text.toString())
        editor?.putString("grade",binding.tvGrade.text.toString())
        editor?.apply()

        super.onPause()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataLoad()
        var pref= context?.getSharedPreferences("userLv",0)
        var a=pref?.getString("lv","0")
        var b=pref?.getString("grade","Bronze")
        binding.tvLevel.text=a
        binding.tvGrade.text=b


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
//                G.title=dialogBinding.etTitle.text.toString()
//                G.review=dialogBinding.etReviewreal.text.toString()
//                list.add(MyBookItem(G.imgUri!!,G.title!!,G.review!!))
//
//                adapter.notifyItemInserted(list.size)
//                binding.recyclerMybook.scrollToPosition(0)
//
//                dialogBinding.etTitle.setText("")
//                dialogBinding.etReviewreal.setText("")
//                Glide.with(requireContext()).load(R.drawable.icon_add).into(dialogBinding.ivMyImg)

                lvUp()
                //dataSave()
                //dia.dismiss()
            }

            dialogBinding.btnCancel.setOnClickListener { dia.dismiss() }
        }
    }

    @SuppressLint("ResourceAsColor")
    fun lvUp(){
        var lv=binding.tvLevel.text.toString().toInt()+1
        binding.tvLevel.text=lv.toString()
        if (binding.tvLevel.text.toString().toInt() in 4..10) {
            binding.tvGrade.text="Silver"
            binding.tvGrade.setTextColor(R.color.silver)}
        else if (binding.tvLevel.text.toString().toInt() in 11..16) {
            binding.tvGrade.text="Gold"
            binding.tvGrade.setTextColor(R.color.gold)}
        else if (binding.tvLevel.text.toString().toInt() in 17..24) {
            binding.tvGrade.text="Platinum"
            binding.tvGrade.setTextColor(R.color.platinum)}
        else  {binding.tvGrade.text="Diamond"
            binding.tvGrade.setTextColor(R.color.diamond)}
    }

    fun dataSave(){
        var firestore=FirebaseFirestore.getInstance()
        var reviewRef:CollectionReference=firestore.collection("review")
        var reviews:MutableMap<String,String> = mutableMapOf()
        reviews.put("cover",G.imgUri!!.toString())
        reviews.put("title",G.title!!)
        reviews.put("text",G.review!!)
        reviewRef.document("MSG_"+System.currentTimeMillis()).set(reviews)
        binding.recyclerMybook.scrollToPosition(list.size-1)

    }

    fun dataLoad(){
        var firestore=FirebaseFirestore.getInstance()
        var reviewRef=firestore.collection("review")
        reviewRef.get().addOnSuccessListener {
            for (snapshot in it){
                var reviews:MutableMap<String,Any> = snapshot.data
                var cover=reviews.get("cover").toString()
                var title=reviews.get("title").toString()
                var text=reviews.get("text").toString()
                list.add(MyBookItem(Uri.parse(cover),title!!,text!!))
            }
            adapter=MyBookAdapter(requireContext(),list)
            binding.recyclerMybook.adapter=adapter
            binding.recyclerMybook.scrollToPosition(list.size-1)
        }
    }
}