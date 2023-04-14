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
    lateinit var binding:ActivityMapBinding
    val mapView: MapView by lazy { MapView(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBefore.setOnClickListener { finish() }

        binding.mapView.addView(mapView)

        var lat:Double = MainActivity().myLocation?.latitude  ?: 37.5663
        var lng:Double = MainActivity().myLocation?.longitude ?: 126.9779
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
        val documents:MutableList<Place>? = MainActivity().searchPlaceResponse?.documents
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












}