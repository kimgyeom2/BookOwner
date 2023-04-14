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
    var searchQuery:String="서점"
    var myLocation: Location?=null
    val providerClient: FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    var searchPlaceResponse: KakaoSearchPlaceResponse?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bnvControl()
        openOrCreateDatabase("interest", MODE_PRIVATE,null)

        binding.btnBookstore.setOnClickListener {
            startActivity(Intent(this,MapActivity::class.java))
        }

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED){
            // 퍼미션 요청 대행사 이용-계약 체결
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }else{
            // 내 위치 요청
            requestMyLocation()
        }
    }
    val permissionLauncher: ActivityResultLauncher<String> =registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result!!) requestMyLocation()
        else Toast.makeText(
            this@MainActivity,
            "위치정보 제공에 동의하지 않았습니다. 검색기능이 제한됩니다",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun requestMyLocation(){
        // 위치검색 기준 설정하는 요청객체
        val request: LocationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,1000).build()

        // 실시간 위치정보를 갱신하도록 요청
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }   // if문 error클릭 자동완성
        providerClient.requestLocationUpdates(request,locationCallback, Looper.getMainLooper())
    }


    private val locationCallback: LocationCallback =object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            myLocation=p0.lastLocation

            //위치 탐색되었으니 실시간 업데이트를 종료
            providerClient.removeLocationUpdates(this) //this : locationCallback 객체

            searchPlace()
        }
    }
    private fun searchPlace(){
        val retrofit: Retrofit = RetrofitHelper.getRetrofitInstance("https://dapi.kakao.com")
        val retrofitApiservice=retrofit.create(RetrofitApiService::class.java)
        retrofitApiservice.searchplace(searchQuery,myLocation?.latitude.toString(),myLocation?.longitude.toString()).enqueue(object :
            Callback<KakaoSearchPlaceResponse> {
            override fun onResponse(
                call: Call<KakaoSearchPlaceResponse>,
                response: Response<KakaoSearchPlaceResponse>
            ) {
                searchPlaceResponse=response.body()

                //Toast.makeText(this@MainActivity, "${searchPlaceResponse?.meta?.total_count}", Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(call: Call<KakaoSearchPlaceResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "서버문제가 있습니다", Toast.LENGTH_SHORT).show()
            }
        })

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