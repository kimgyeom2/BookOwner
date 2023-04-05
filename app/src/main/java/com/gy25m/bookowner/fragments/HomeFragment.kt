package com.gy25m.bookowner.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.gy25m.bookowner.R
import com.gy25m.bookowner.activites.MainActivity
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

        var main=requireActivity() as MainActivity
        main.supportFragmentManager.beginTransaction().replace(binding.frame.id,BestFragment()).commit()
        binding.etSearch.setOnEditorActionListener { textView, i, keyEvent ->
            search()
            false
        }
        binding.ivSearch.setOnClickListener {search()}

        binding.btnBest.isSelected=true
        binding.btnBest.setOnClickListener {
            it.isSelected=true
            binding.btnNew.isSelected=false
            main.supportFragmentManager.beginTransaction().replace(binding.frame.id,BestFragment()).commit()
        }
        binding.btnNew.setOnClickListener {
            it.isSelected=true
            binding.btnBest.isSelected=false
            main.supportFragmentManager.beginTransaction().replace(binding.frame.id,NewFragment()).commit()
        }

    }

    fun search(){
        val intent = Intent(activity,SearchActivity::class.java)
        intent.putExtra("bookName",binding.etSearch.text.toString())
        startActivity(intent)
        binding.etSearch.text.clear()
        binding.etSearch.clearFocus()
    }

}