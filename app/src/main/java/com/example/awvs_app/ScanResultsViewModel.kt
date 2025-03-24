package com.example.awvs_app

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.sign_in.RetrofitClient
import com.example.data.repository.sign_in.ScanResultsRepository
import com.example.models.ScanResults
import kotlinx.coroutines.launch

class ScanResultsViewModel : ViewModel() {

    private val repository = ScanResultsRepository(RetrofitClient.apiService)

    private val _scanResults = mutableStateOf<ScanResults?>(null)
    val scanResults: State<ScanResults?> = _scanResults

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun fetchScanResults(requestBody: Map<String,  @JvmSuppressWildcards Any>) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.getScanResults(requestBody)
                _scanResults.value = result
                _errorMessage.value = null
                Log.d("ScanResultsViewModel", "API Response: $result")
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error"
                Log.e("ScanResultsViewModel", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}

