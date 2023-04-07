package com.gy25m.bookowner.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gy25m.bookowner.adapters.FavorAdapter
import com.gy25m.bookowner.databinding.FragmentChatBinding
import com.gy25m.bookowner.databinding.FragmentFavorBinding
import com.gy25m.bookowner.databinding.FragmentHomeBinding
import com.gy25m.bookowner.model.FavorItem

class FavorFragment : Fragment() {

    private lateinit var binding: FragmentFavorBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentFavorBinding.inflate(inflater,container,false)
        return binding.root

    }

    @SuppressLint("Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var items= mutableListOf<FavorItem>()
        var db=requireActivity().openOrCreateDatabase("interest", AppCompatActivity.MODE_PRIVATE,null)


        var cursor = db.rawQuery("SELECT * FROM book",null)
        var num=cursor.count
        cursor.moveToFirst()
        if (cursor!=null){
        for (a in 0 until num) {
            var num = cursor.getInt(0)
            var cover = cursor.getString(1)
            var title = cursor.getString(2)
            var des = cursor.getString(3)

            items.add(FavorItem(cover, title, des))
            cursor.moveToNext()
        }
        binding.recyclerFavor.adapter=FavorAdapter(requireContext(),items)}
    }
}
