package com.gy25m.bookowner.fragments


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.gy25m.bookowner.G
import com.gy25m.bookowner.R
import com.gy25m.bookowner.adapters.ChatAdapter
import com.gy25m.bookowner.databinding.DialogAddChatBinding
import com.gy25m.bookowner.databinding.FragmentChatBinding
import com.gy25m.bookowner.model.ChatItem


class ChatFragment : Fragment() {

    private lateinit var binding:FragmentChatBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentChatBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var dialogBinding=DialogAddChatBinding.inflate(layoutInflater)

        val resultLauncher :ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),object :
                ActivityResultCallback<ActivityResult> {
                override fun onActivityResult(result: ActivityResult?) {
                    if (result?.resultCode == Activity.RESULT_CANCELED) return
                    G.imgUri= result?.data?.data!!
                    Glide.with(this@ChatFragment).load(G.imgUri).into(dialogBinding.ivFeed)
                }
            })

        var dia=AlertDialog.Builder(activity).setView(dialogBinding.root).create()
        var chatItem= mutableListOf<ChatItem>()
        binding.btnAddReview.setOnClickListener {
            dia.show()
            dialogBinding.ivFeed.setOnClickListener {
                val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
                resultLauncher.launch(intent)

                dialogBinding.btnConfirm.setOnClickListener {
                    if (G.imgUri !=null && dialogBinding.etFeed.text.toString()!=""){
                        chatItem.add(ChatItem(G.userId!!,G.imgUri.toString(),dialogBinding.etFeed.text.toString()))
                        var adapter=ChatAdapter(chatItem,requireActivity())
                        binding.recyclerReview.adapter=adapter
                        adapter.notifyItemInserted(0)
                        datasave()
                        dialogBinding.etFeed.text=null
                        Glide.with(requireActivity()).load(R.drawable.icon_add).into(dialogBinding.ivFeed)
                        dia.dismiss()
                    }else{
                        Toast.makeText(requireContext(), "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show()
                    }
                }
                dialogBinding.btnCancel.setOnClickListener { dia.dismiss() }


            }
        }

    }

    fun datasave(){}

}