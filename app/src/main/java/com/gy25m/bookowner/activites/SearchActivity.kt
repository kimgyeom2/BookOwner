package com.gy25m.bookowner.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gy25m.bookowner.R
import com.gy25m.bookowner.adapters.SearchAdapter
import com.gy25m.bookowner.databinding.ActivitySearchBinding
import com.gy25m.bookowner.model.AladinApiResponce
import com.gy25m.bookowner.network.RetrofitApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class SearchActivity : AppCompatActivity() {

    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivBefore.setOnClickListener{finish()}

        // 검색어
        var bookName=intent.getStringExtra("bookName")

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://www.aladin.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService=retrofit.create(RetrofitApiService::class.java)
        val call: Call<AladinApiResponce> = retrofitService.searchBook(bookName!!,"ttbrlarua72021535001")

        call.enqueue(object : Callback<AladinApiResponce>{
            override fun onResponse(
                call: Call<AladinApiResponce>,
                response: Response<AladinApiResponce>
            ) {
                val ala=response.body()
                binding.recyclerSearch.adapter=SearchAdapter(this@SearchActivity,ala!!.item)

            }

            override fun onFailure(call: Call<AladinApiResponce>, t: Throwable) {
                Toast.makeText(this@SearchActivity, "mmmmmmmmmmmmmmm", Toast.LENGTH_SHORT).show()
                Log.i("zzzzzzzzzzz",t.message.toString())
            }

        })
    }
}