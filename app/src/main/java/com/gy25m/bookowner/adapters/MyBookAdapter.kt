package com.gy25m.bookowner.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.gy25m.bookowner.databinding.RecycleritemMybookBinding
import com.gy25m.bookowner.model.MyBookItem

class MyBookAdapter(var context:Context,var list:MutableList<MyBookItem>) : Adapter<MyBookAdapter.VH>() {
    var firestore=FirebaseFirestore.getInstance()
    var reviewRef=firestore.collection("review")
    inner class VH(var binding:RecycleritemMybookBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RecycleritemMybookBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.tvTitle.text=list[position].title
        holder.binding.tvReview.text=list[position].review
        Glide.with(context).load(list[position].imgUrl).into(holder.binding.ivBookCover)

        holder.binding.btnDelete.setOnClickListener {
            reviewRef.whereEqualTo("title",list[position].title).get().addOnSuccessListener {
                for (snapshot in it){
                    var aa:MutableMap<String,Any> = snapshot.data
                    reviewRef.document(aa.get("docpath").toString()).delete()
                }
            }
            list.removeAt(position)
            notifyDataSetChanged()
        }
    }
}