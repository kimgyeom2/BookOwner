package com.gy25m.bookowner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gy25m.bookowner.databinding.FragmentChatBinding
import com.gy25m.bookowner.databinding.FragmentFavorBinding
import com.gy25m.bookowner.databinding.FragmentHomeBinding

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
}
