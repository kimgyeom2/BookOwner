package com.gy25m.bookowner.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.gy25m.bookowner.R
import com.gy25m.bookowner.databinding.DialogItemdetailBinding
import com.gy25m.bookowner.databinding.RecycleritemFavorBinding
import com.gy25m.bookowner.model.FavorItem

class FavorAdapter(var context: Context,var items:MutableList<FavorItem>) : Adapter<FavorAdapter.VH>() {
    inner class VH(var binding:RecycleritemFavorBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH=VH(RecycleritemFavorBinding.inflate(LayoutInflater.from(context),parent,false))

    override fun getItemCount(): Int =items.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: VH, position: Int) {
         var item=items[position]
        holder.binding.tvFavorTitle.text=item.favorTitle
        Glide.with(context).load(item.favorCover).into(holder.binding.ivFavorCover)

        holder.itemView.setOnClickListener {
            var dialogBinding= DialogItemdetailBinding.inflate(LayoutInflater.from(context))
            var tit= HtmlCompat.fromHtml(item.favorTitle, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
            dialogBinding.tvTitle.text=tit
            var des= HtmlCompat.fromHtml(item.favorDes, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
            dialogBinding.detailDescription.text=des
            Glide.with(context).load(item.favorCover).into(dialogBinding.detailCover)
            AlertDialog.Builder(context).setView(dialogBinding.root).show()
        }
        holder.binding.heart.setOnClickListener {
            var db=context.openOrCreateDatabase("interest",MODE_PRIVATE,null)
            db.execSQL(" DELETE FROM book WHERE title='${item.favorTitle}'",)
            items.removeAt(position)
            notifyDataSetChanged()
        }
    }
}