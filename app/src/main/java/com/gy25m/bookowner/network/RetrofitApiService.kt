package com.gy25m.bookowner.network

import com.gy25m.bookowner.model.AladinApiResponce
import com.gy25m.bookowner.model.AladinBookList
import com.gy25m.bookowner.model.KakaoSearchPlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApiService {
    @GET("ttb/api/ItemSearch.aspx?MaxResults=10&start=1&SearchTarget=Book&output=js&Version=20131101")
    fun searchBook(@Query("Query") query: String,@Query("ttbkey") ttbkey: String): Call<AladinApiResponce>

    @GET("ttb/api/ItemList.aspx?SearchTarget=Book&MaxResults=10&output=js&Version=20131101")
    fun booklist(@Query("QueryType") querytype:String,@Query("ttbkey") ttbkey: String ) :Call<AladinBookList>


    @Headers("Authorization: KakaoAK c086881d21eb2907734d818e601bd66d")
    @GET("/v2/local/search/keyword.json")
    fun searchplace(@Query("query") query:String,@Query("y")latitude:String,@Query("x")longitude:String):Call<KakaoSearchPlaceResponse>

}
