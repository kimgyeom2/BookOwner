package com.gy25m.bookowner.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.gy25m.bookowner.R
import com.gy25m.bookowner.databinding.ActivityMainBinding
import com.gy25m.bookowner.fragments.BestFragment
import com.gy25m.bookowner.fragments.ChatFragment
import com.gy25m.bookowner.fragments.FavorFragment
import com.gy25m.bookowner.fragments.HomeFragment
import com.gy25m.bookowner.fragments.MyBookFragment
import kotlinx.coroutines.selects.select

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bnvControl()

    }

    private fun bnvControl(){
        binding.bnv.run {
            setOnItemSelectedListener {
                when(it.itemId){
                    R.id.bnv_tab1 -> changeFragment(HomeFragment())
                    R.id.bnv_tab2 -> changeFragment(FavorFragment())
                    R.id.bnv_tab3 -> changeFragment(ChatFragment())
                    R.id.bnv_tab4 -> changeFragment(MyBookFragment())
                }
                true
            }
            selectedItemId=R.id.bnv_tab1
        }
    }
    fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentLayout.id, fragment).commit()
    }


}