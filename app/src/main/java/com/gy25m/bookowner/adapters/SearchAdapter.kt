package com.gy25m.bookowner.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.gy25m.bookowner.databinding.RecycleritemSearchBinding
import com.gy25m.bookowner.model.Book

class SearchAdapter(var context:Context,var items:MutableList<Book>) : Adapter<SearchAdapter.VH>() {
    inner class VH(var binding:RecycleritemSearchBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RecycleritemSearchBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int =items.size


    override fun onBindViewHolder(holder: VH, position: Int) {
        var item=items[position]
        holder.binding.tvTitle.text=item.title
        holder.binding.tvAuthor.text=item.author
        holder.binding.tvPubdate.text=item.pubDate
        holder.binding.tvDescription.text=item.description
        Glide.with(context).load(item.cover).into(holder.binding.ivBookCover)

    }
}