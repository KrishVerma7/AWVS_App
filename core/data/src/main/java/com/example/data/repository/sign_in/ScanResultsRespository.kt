package com.example.data.repository.sign_in

import com.example.models.ScanResults
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScanResultsRepository(private val apiService: ApiService) {

    suspend fun getScanResults(requestBody: Map<String, Any>): ScanResults {
        return apiService.getScanResults(requestBody)
    }

}

