package com.gy25m.bookowner.activites

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.gy25m.bookowner.R
import com.gy25m.bookowner.databinding.ActivityMainBinding
import com.gy25m.bookowner.fragments.ChatFragment
import com.gy25m.bookowner.fragments.FavorFragment
import com.gy25m.bookowner.fragments.HomeFragment
import com.gy25m.bookowner.fragments.MyBookFragment
import com.gy25m.bookowner.model.KakaoSearchPlaceResponse
import com.gy25m.bookowner.network.RetrofitApiService
import com.gy25m.bookowner.network.RetrofitHelper
import com.kakao.util.maps.helper.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


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