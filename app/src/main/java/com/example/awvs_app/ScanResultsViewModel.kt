package com.example.awvs_app

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.awvs_app.presentation.feature.home.ScanResultActivity
import com.example.data.repository.sign_in.ApiService
import com.example.models.ScanResults
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class ScanResultsViewModel : ViewModel() {

    private val _scanResults = mutableStateOf<ScanResults?>(null)
    val scanResults: State<ScanResults?> = _scanResults

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun fetchScanResults(apiService: ApiService, requestBody: Map<String, @JvmSuppressWildcards Any?>, activity: Activity,targetUrl:String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = apiService.getScanResults(requestBody as Map<String, @JvmSuppressWildcards Any>)
//                val parsedResult = parseScanResults(result.toString())
//                _scanResults.value = parsedResult
                _scanResults.value = result
                _errorMessage.value = null

                Log.d("ScanResultsViewModel", "API Response: $result")

                val intent = Intent(activity, ScanResultActivity::class.java).apply {
//                    putExtra("scanResults", Json.encodeToString(parsedResult))
                    putExtra("scanResults", Gson().toJson(result))
                    putExtra("targetUrl", targetUrl)
                }
                activity.startActivity(intent)

            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error"
                Log.e("ScanResultsViewModel", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun parseScanResults(jsonString: String): ScanResults? {
        return try {
            Json { ignoreUnknownKeys = true }.decodeFromString<ScanResults>(jsonString)
        } catch (e: Exception) {
            Log.e("ScanResultsViewModel", "Parsing Error: ${e.message}")
            null
        }
    }
}