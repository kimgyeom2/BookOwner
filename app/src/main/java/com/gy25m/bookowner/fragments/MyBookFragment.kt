package com.gy25m.bookowner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gy25m.bookowner.databinding.FragmentHomeBinding
import com.gy25m.bookowner.databinding.FragmentMybookBinding

class MyBookFragment : Fragment() {

    private lateinit var binding: FragmentMybookBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMybookBinding.inflate(inflater,container,false)
        return binding.root
    }
}