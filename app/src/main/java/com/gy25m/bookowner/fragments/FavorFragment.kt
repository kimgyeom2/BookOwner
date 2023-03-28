package com.gy25m.bookowner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gy25m.bookowner.databinding.FragmentHomeBinding

class FavorFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding= FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }
}
