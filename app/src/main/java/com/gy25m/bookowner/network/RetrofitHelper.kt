package com.gy25m.bookowner.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitHelper {
    companion object{
        fun getRetrofitInstance(baseUrl: String): Retrofit {
            // 로깅 인터셉터 생성
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY // 로그 레벨 설정

            // OkHttpClient를 생성하고 로깅 인터셉터를 추가
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            // Retrofit 인스턴스 생성 시 OkHttpClient 인스턴스를 사용
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // 여기에서 OkHttpClient 인스턴스를 설정
                .build()
            return retrofit
        }
    }
}