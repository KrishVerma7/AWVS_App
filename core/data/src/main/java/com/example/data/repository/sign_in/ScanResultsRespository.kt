package com.example.data.repository.sign_in

import com.example.models.ScanResults

class ScanResultsRepository(private val apiService: ApiService) {

    suspend fun getScanResults(requestBody: Map<String, Any>): ScanResults {
        return apiService.getScanResults(requestBody)
    }

}

