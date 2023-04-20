package com.gy25m.bookowner.adapters

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.gy25m.bookowner.R
import com.gy25m.bookowner.databinding.DialogItemdetailBinding
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
        holder.binding.tvCategori.text=item.categoryName
        Glide.with(context).load(item.cover).into(holder.binding.ivBookCover)
        
        holder.binding.ivFavor.setOnClickListener {
            Toast.makeText(context, "관심도서 추가!", Toast.LENGTH_SHORT).show()
            var db=context.openOrCreateDatabase("interest",MODE_PRIVATE,null)
            var cover=item.cover.replace("'","")
            var title=item.title.replace("'","")
            var description=item.description.replace("'","")
            db.execSQL("INSERT INTO book (cover,title,description) VALUES ('${cover}','${title}','${description}')")

         }


        holder.itemView.setOnClickListener {
            var dialogBinding=DialogItemdetailBinding.inflate(LayoutInflater.from(context))
            var tit=HtmlCompat.fromHtml(item.title,HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
            dialogBinding.tvTitle.text=tit
            var des=HtmlCompat.fromHtml(item.description,HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
            dialogBinding.detailDescription.text=des
            Glide.with(context).load(item.cover).into(dialogBinding.detailCover)
            AlertDialog.Builder(context).setView(dialogBinding.root).show()
        }

    }

}