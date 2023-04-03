package com.gy25m.bookowner.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gy25m.bookowner.activites.SearchActivity
import com.gy25m.bookowner.databinding.FragmentFavorBinding
import com.gy25m.bookowner.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivSearch.setOnClickListener {
            val intent = Intent(activity,SearchActivity::class.java)
            intent.putExtra("bookName",binding.etSearch.text.toString())
            startActivity(intent)
        }

        binding.btnBest.isSelected=true
        binding.btnBest.setOnClickListener {
            binding.btnBest.isSelected=true
            binding.btnNew.isSelected=false
        }
        binding.btnNew.setOnClickListener {
            binding.btnNew.isSelected=true
            binding.btnBest.isSelected=false
        }

    }
}