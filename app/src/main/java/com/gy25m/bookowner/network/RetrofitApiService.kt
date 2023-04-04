package com.gy25m.bookowner.network

import com.gy25m.bookowner.model.AladinApiResponce
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {
    @GET("ttb/api/ItemSearch.aspx?QueryType=Title&MaxResults=5&start=1&SearchTarget=Book&output=js&Version=20131101")
    fun searchBook(@Query("Query") query: String,@Query("ttbkey") ttbkey: String): Call<AladinApiResponce>
}
