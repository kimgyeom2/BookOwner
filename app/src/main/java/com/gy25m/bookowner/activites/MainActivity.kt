package com.gy25m.bookowner.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gy25m.bookowner.R
import com.gy25m.bookowner.databinding.ActivityMainBinding
import com.gy25m.bookowner.fragments.ChatFragment
import com.gy25m.bookowner.fragments.FavorFragment
import com.gy25m.bookowner.fragments.HomeFragment
import com.gy25m.bookowner.fragments.MyBookFragment
import kotlinx.coroutines.selects.select

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val fragments= arrayOf(HomeFragment(),FavorFragment(),ChatFragment(),MyBookFragment())
        supportFragmentManager.beginTransaction().add(R.id.fragment_layout,fragments[0]).commit()

        binding.bnv.setOnItemSelectedListener{
            if (it.itemId==R.id.bnv_tab1) supportFragmentManager.beginTransaction().replace(R.id.fragment_layout,fragments[0]).commit()
            else if (it.itemId==R.id.bnv_tab2) supportFragmentManager.beginTransaction().replace(R.id.fragment_layout,fragments[1]).commit()
            else if (it.itemId==R.id.bnv_tab3) supportFragmentManager.beginTransaction().replace(R.id.fragment_layout,fragments[2]).commit()
            else if (it.itemId==R.id.bnv_tab4) supportFragmentManager.beginTransaction().replace(R.id.fragment_layout,fragments[3]).commit()
            return@setOnItemSelectedListener true
        }

        binding.ivSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }
}