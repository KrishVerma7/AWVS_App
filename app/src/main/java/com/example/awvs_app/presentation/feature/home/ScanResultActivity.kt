package com.example.awvs_app.presentation.feature.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.models.ScanResults
import com.google.gson.Gson

class ScanResultActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val json = intent.getStringExtra("scanResults")
        val scanResults:ScanResults? = Gson().fromJson(json, ScanResults::class.java)
        val targetUrl = intent.getStringExtra("targetUrl") ?: "N/A"

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    scanResults?.let {
                        DisplayScanResults(
                            it,
                            targetUrl = targetUrl
                        )
                    } ?: Text("No Results Found", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}
