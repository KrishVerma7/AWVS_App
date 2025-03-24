package com.example.data.repository.sign_in

import com.example.models.ScanResults
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("/scan") // Replace with your API endpoint
    suspend fun getScanResults(@Body requestBody: Map<String,  @JvmSuppressWildcards Any>): ScanResults
}
