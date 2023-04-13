package com.gy25m.bookowner.activites

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gy25m.bookowner.R
import com.gy25m.bookowner.databinding.ActivityMainBinding
import com.gy25m.bookowner.fragments.ChatFragment
import com.gy25m.bookowner.fragments.FavorFragment
import com.gy25m.bookowner.fragments.HomeFragment
import com.gy25m.bookowner.fragments.MyBookFragment
import com.kakao.util.maps.helper.Utility


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bnvControl()
        openOrCreateDatabase("interest", MODE_PRIVATE,null)

        binding.btnBookstore.setOnClickListener {
            startActivity(Intent(this,MapActivity::class.java))
        }
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