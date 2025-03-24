package com.example.data.repository.sign_in

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://5358-122-161-65-106.ngrok-free.app/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.MINUTES)   // Connection timeout
        .readTimeout(10, TimeUnit.MINUTES)      // Reading response timeout
        .writeTimeout(10, TimeUnit.MINUTES)     // Writing request timeout
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
