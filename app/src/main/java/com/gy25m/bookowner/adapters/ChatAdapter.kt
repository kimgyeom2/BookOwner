package com.gy25m.bookowner.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.gy25m.bookowner.G
import com.gy25m.bookowner.R
import com.gy25m.bookowner.activites.CommentActivity
import com.gy25m.bookowner.databinding.RecycleritemChatBinding
import com.gy25m.bookowner.model.ChatItem

class ChatAdapter(private var chatItem: MutableList<ChatItem>, var context: Context) : Adapter<ChatAdapter.VH>() {
    inner class VH(var binding:RecycleritemChatBinding) : ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RecycleritemChatBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int = chatItem.size


    override fun onBindViewHolder(holder: VH, position: Int) {
        var item=chatItem[position]
        holder.binding.id.text= item.id+"님의 게시물"
        holder.binding.tvChat.text=item.text
        Glide.with(context).load(item.img).into(holder.binding.ivChat)

        holder.binding.btnHt.setOnClickListener {
            it.isSelected = it.isSelected==false
        }

        holder.binding.btnChat.setOnClickListener {
            var intent=Intent(context,CommentActivity::class.java)
            intent.putExtra("img",item.img)
            intent.putExtra("id",item.id)
            intent.putExtra("text",item.text)
            intent.putExtra("tag",item.tag)
            context.startActivity(intent)
        }
    }
}