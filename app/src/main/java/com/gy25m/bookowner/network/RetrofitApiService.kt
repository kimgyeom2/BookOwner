package com.gy25m.bookowner.network

import com.gy25m.bookowner.model.AladinApiResponce
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {
    @GET("/ttb/api/ItemSearch.aspx?ttbkey=ttbrlarua72021535001&QueryType=Title&MaxResults=10&start=1&SearchTarget=Book&output=js&Version=20131101")
    fun searchBook(@Query("Query") query: String): Call<AladinApiResponce>
}