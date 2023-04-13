package com.gy25m.bookowner.activites

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.gy25m.bookowner.R
import com.gy25m.bookowner.databinding.ActivityMapBinding
import com.gy25m.bookowner.model.KakaoSearchPlaceResponse
import com.gy25m.bookowner.model.Place
import com.gy25m.bookowner.network.RetrofitApiService
import com.gy25m.bookowner.network.RetrofitHelper
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MapActivity : AppCompatActivity() {
    var searchQuery:String="서점"
    lateinit var binding:ActivityMapBinding
    var myLocation: Location?=null
    val providerClient: FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    var searchPlaceResponse:KakaoSearchPlaceResponse?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBefore.setOnClickListener { finish() }
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_DENIED){
            // 퍼미션 요청 대행사 이용-계약 체결
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }else{
            // 내 위치 요청
            requestMyLocation()
        }

        val mapView: MapView by lazy { MapView(this) }
        binding.mapView.addView(mapView)

        var lat:Double = myLocation?.latitude  ?: 37.5663
        var lng:Double = myLocation?.longitude ?: 126.9779
        val mp= MapPoint.mapPointWithGeoCoord(lat,lng)
        mapView.setMapCenterPointAndZoomLevel(mp,5,false)
        mapView.zoomIn(true)
        mapView.zoomOut(true)

        var marker= MapPOIItem()
        marker.apply {
            itemName="ME"
            mapPoint=mp
            markerType= MapPOIItem.MarkerType.BluePin
            selectedMarkerType= MapPOIItem.MarkerType.RedPin
        }
        mapView.addPOIItem(marker)

        // 검색 장소들의 마커 추가
        val documents:MutableList<Place>? = searchPlaceResponse?.documents
        documents?.forEach {
            val point:MapPoint=MapPoint.mapPointWithGeoCoord(it.y.toDouble(),it.x.toDouble())

            var marker= MapPOIItem().apply {
                mapPoint=point
                itemName=it.place_name
                markerType= MapPOIItem.MarkerType.YellowPin
                selectedMarkerType= MapPOIItem.MarkerType.RedPin
                // 마커객체에 보관하고 싶은 데이터가 있다면
                // 즉. 해당 마커에 관련된 정보를 가지고있는 객체를 마커에 저장해놓기
                userObject=it
            }
            mapView.addPOIItem(marker)
        }




    }//oncreate


    val permissionLauncher: ActivityResultLauncher<String> =registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result!!) requestMyLocation()
        else Toast.makeText(
            this@MapActivity,
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
        val retrofit: Retrofit =RetrofitHelper.getRetrofitInstance("https://dapi.kakao.com")
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
                Toast.makeText(this@MapActivity, "서버문제가 있습니다", Toast.LENGTH_SHORT).show()
            }
        })

    }



}