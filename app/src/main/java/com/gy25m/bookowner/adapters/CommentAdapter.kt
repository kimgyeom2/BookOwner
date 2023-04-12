package com.gy25m.bookowner.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gy25m.bookowner.databinding.RecycleritemCommentBinding
import com.gy25m.bookowner.model.CommentItem

class CommentAdapter(var context: Context,var items:MutableList<CommentItem>) : Adapter<CommentAdapter.VH>() {
    inner class VH(var binding:RecycleritemCommentBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RecycleritemCommentBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int =items.size


    override fun onBindViewHolder(holder: VH, position: Int) {
        var item=items[position]
        holder.binding.replyId.text=item.id+"님의 댓글"
        holder.binding.replyText.text=item.text
    }
}