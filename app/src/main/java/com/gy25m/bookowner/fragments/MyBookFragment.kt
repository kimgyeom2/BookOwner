package com.gy25m.bookowner.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataLoad()
        var infoDia=AlertDialog.Builder(requireContext()).setView(layoutInflater.inflate(R.layout.dialog_info,null)).create()
        binding.btnInfo.setOnClickListener{
            infoDia.show()
        }
        var lv=0
        binding.tvLevel.text="$lv"

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
                list.add(MyBookItem(G.imgUri!!,G.title!!,G.review!!))

                adapter.notifyItemInserted(list.size)
                binding.recyclerMybook.scrollToPosition(0)
                dataSave()
                dialogBinding.etTitle.setText("")
                dialogBinding.etReviewreal.setText("")
                Glide.with(requireContext()).load(R.drawable.icon_add).into(dialogBinding.ivMyImg)
                binding.tvLevel.text="${lv+1}"
                dia.dismiss()

            }

            dialogBinding.btnCancel.setOnClickListener { dia.dismiss() }
        }
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