package com.gy25m.bookowner.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.gy25m.bookowner.G
import com.gy25m.bookowner.databinding.FragmentMybookBinding
import com.gy25m.bookowner.databinding.RecycleritemMybookBinding
import com.gy25m.bookowner.model.MyBookItem

class MyBookAdapter(var context:Context,var list:MutableList<MyBookItem>,var bind:FragmentMybookBinding) : Adapter<MyBookAdapter.VH>() {
    private var firestore = FirebaseFirestore.getInstance()
    private var reviewRef = firestore.collection("book")
    private var userRef = firestore.collection("userInfo")

    inner class VH(var binding: RecycleritemMybookBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RecycleritemMybookBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.tvTitle.text = list[position].title
        holder.binding.tvReview.text = list[position].review
        Glide.with(context).load(list[position].imgUrl).into(holder.binding.ivBookCover)

        holder.binding.btnDelete.setOnClickListener {
            reviewRef.whereEqualTo("title", list[position].title).get().addOnSuccessListener {
                for (snapshot in it) {
                    var aa: MutableMap<String, Any> = snapshot.data
                    reviewRef.document(aa["docpath"].toString()).delete()
                }
                list.removeAt(position)
                bind.tvLevel.text = list.size.toString()
                if (list.size in 0 until 4) {
                    bind.tvGrade.setTextColor(Color.parseColor("#9E6613"))
                    bind.tvGrade.text = "Bronze"
                }else if (list.size in 4 until 8) {
                    bind.tvGrade.setTextColor(Color.parseColor("#ACABAB"))
                    bind.tvGrade.text = "Silver"
                } else if (list.size in 8 until 13) {
                    bind.tvGrade.setTextColor(Color.parseColor("#F4E23C"))
                    bind.tvGrade.text = "Gold"
                } else if (list.size in 13 until 19) {
                    bind.tvGrade.setTextColor(Color.parseColor("#13BCAC"))
                    bind.tvGrade.text = "Platinum"
                } else if (list.size >= 30) {
                    bind.tvGrade.setTextColor(Color.parseColor("#53B5E1"))
                    bind.tvGrade.text = "Diamond"
                }
                userRef.document("${G.userId}").update("lv", list.size)
                userRef.document("${G.userId}").update("grade", bind.tvGrade.text)
                notifyDataSetChanged()
            }
        }
    }
}