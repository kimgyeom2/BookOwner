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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.gy25m.bookowner.G
import com.gy25m.bookowner.R
import com.gy25m.bookowner.adapters.ChatAdapter
import com.gy25m.bookowner.databinding.DialogAddChatBinding
import com.gy25m.bookowner.databinding.FragmentChatBinding
import com.gy25m.bookowner.model.ChatItem


class ChatFragment : Fragment() {

    private lateinit var binding:FragmentChatBinding
    var chatItem= mutableListOf<ChatItem>()
    lateinit var adapter:ChatAdapter
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
        dataload()

        val resultLauncher :ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),object :
                ActivityResultCallback<ActivityResult> {
                override fun onActivityResult(result: ActivityResult?) {
                    if (result?.resultCode == Activity.RESULT_CANCELED) return
                    G.img= result?.data?.data!!
                    Glide.with(this@ChatFragment).load(G.img).into(dialogBinding.ivFeed)
                }
            })

        var dia=AlertDialog.Builder(activity).setView(dialogBinding.root).create()

        binding.btnAddReview.setOnClickListener {
            dia.show()
            dialogBinding.ivFeed.setOnClickListener {
                val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
                resultLauncher.launch(intent)

                dialogBinding.btnConfirm.setOnClickListener {
                    if (G.img !=null && dialogBinding.etFeed.text.toString()!=""){
                        G.text=dialogBinding.etFeed.text.toString()
                        chatItem.add(ChatItem(G.userId!!,G.img.toString(),G.text.toString()))
                        adapter.notifyItemInserted(chatItem.size)
                        binding.recyclerReview.scrollToPosition(chatItem.size-1)
                        datasave()
                        dialogBinding.etFeed.text=null
                        Glide.with(requireActivity()).load(R.drawable.icon_add).into(dialogBinding.ivFeed)
                        dialogBinding.etFeed.clearFocus()
                        dia.dismiss()
                    }else{
                        Toast.makeText(requireContext(), "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show()
                    }
                }

                dialogBinding.btnCancel.setOnClickListener { dia.dismiss() }


            }
        }

    }
    var firestore=FirebaseFirestore.getInstance()
    var chatRef=firestore.collection("feed")
    var firestorage=FirebaseStorage.getInstance()
    fun datasave(){
        var map= mutableMapOf<String,String>()
        map.put("id",G.userId.toString())
        map.put("img",G.img.toString())
        map.put("text",G.text.toString())
        chatRef.document("feed_"+System.currentTimeMillis()).set(map)

        firestorage.getReference( ).putFile(G.img!!)
    }
    fun dataload(){
        chatRef.get().addOnSuccessListener {

            for(snapshot in it){
                var feeds = snapshot.data
                var id=feeds.get("id").toString()
                var img=feeds.get("img").toString()
                var text=feeds.get("text").toString()
                chatItem.add(ChatItem(id,img,text))
            }
            adapter=ChatAdapter(chatItem,requireActivity())
            binding.recyclerReview.adapter=adapter
            binding.recyclerReview.scrollToPosition(chatItem.size-1)
        }
    }


}