package com.gy25m.bookowner.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.gy25m.bookowner.R
import com.gy25m.bookowner.databinding.DialogItemdetailBinding
import com.gy25m.bookowner.databinding.RecycleritemBestBinding
import com.gy25m.bookowner.model.Book
import com.gy25m.bookowner.model.Hot

class NewAdapter(var context: Context, var books:MutableList<Hot>) :Adapter<NewAdapter.VH>() {
    inner class VH(var binding:RecycleritemBestBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
       return VH(RecycleritemBestBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int =books.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(context).load(books[position].cover).into(holder.binding.ivBest)

        holder.itemView.setOnClickListener {
            var item=books[position]
            var dialogBinding= DialogItemdetailBinding.inflate(LayoutInflater.from(context))
            dialogBinding.tvTitle.text=item.title
            var des= HtmlCompat.fromHtml(item.description, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
            dialogBinding.detailDescription.text=des
            Glide.with(context).load(item.cover).into(dialogBinding.detailCover)
            AlertDialog.Builder(context).setView(dialogBinding.root).show()
        }
    }
}