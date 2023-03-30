package com.gy25m.bookowner.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gy25m.bookowner.databinding.RecycleritemMybookBinding
import com.gy25m.bookowner.model.MyBookItem

class MyBookAdapter(var context:Context,var list:MutableList<MyBookItem>) : Adapter<MyBookAdapter.VH>() {
    inner class VH(var binding:RecycleritemMybookBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RecycleritemMybookBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.tvTitle.text=list[position].title
        holder.binding.tvReview.text=list[position].review


    }
}