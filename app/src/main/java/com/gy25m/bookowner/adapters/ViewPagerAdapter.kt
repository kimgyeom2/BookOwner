package com.gy25m.bookowner.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gy25m.bookowner.databinding.RecycleritemViewpagerBinding
import com.gy25m.bookowner.network.BestItem

class ViewPagerAdapter(var context: Context,var items:MutableList<BestItem>) : Adapter<ViewPagerAdapter.VH>() {

    inner class VH(var binding: RecycleritemViewpagerBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RecycleritemViewpagerBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        TODO("Not yet implemented")
    }
}