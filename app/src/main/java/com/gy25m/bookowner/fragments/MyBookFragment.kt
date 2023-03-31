package com.gy25m.bookowner.fragments

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.gy25m.bookowner.G
import com.gy25m.bookowner.R
import com.gy25m.bookowner.adapters.MyBookAdapter
import com.gy25m.bookowner.databinding.DialogAddBookBinding
import com.gy25m.bookowner.databinding.FragmentMybookBinding
import com.gy25m.bookowner.model.MyBookItem

class MyBookFragment : Fragment() {

    private lateinit var binding: FragmentMybookBinding

    var list: MutableList<MyBookItem> = mutableListOf()
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

        binding.btnInfo.setOnClickListener{
            AlertDialog.Builder(requireContext()).setView(layoutInflater.inflate(R.layout.dialog_info,null)).show()
        }


        var dialogBinding=DialogAddBookBinding.inflate(layoutInflater)

        val resultLauncher :ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),object :ActivityResultCallback<ActivityResult>{
            override fun onActivityResult(result: ActivityResult?) {
                if (result?.resultCode == Activity.RESULT_CANCELED) return
                G.imgUri= result?.data?.data!!
                Glide.with(this@MyBookFragment).load(G.imgUri).into(dialogBinding.ivMyImg)

            }
        })

        binding.addBook.setOnClickListener{
            var dia=AlertDialog.Builder(requireContext()).setView(dialogBinding.root).show()

            dialogBinding.ivMyImg.setOnClickListener {
                val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
                resultLauncher.launch(intent)
            }

            dialogBinding.btnConfirm.setOnClickListener {
                var title=dialogBinding.etTitle.text.toString()
                var review=dialogBinding.etReviewreal.text.toString()
                list.add(MyBookItem(G.imgUri!!,title,review))



                var adapter=MyBookAdapter(requireContext(),list)
                binding.recyclerMybook.adapter=adapter
                adapter.notifyItemInserted(0)
                binding.recyclerMybook.scrollToPosition(0)
                dia.dismiss()
            }

        }


    }
}