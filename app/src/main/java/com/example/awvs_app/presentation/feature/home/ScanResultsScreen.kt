package com.example.awvs_app.presentation.feature.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.awvs_app.ScanResultsViewModel

//@Composable
//fun ScanResultsScreen(viewModel: ScanResultsViewModel = viewModel()) {
//    val scanResults = viewModel.scanResults.value
//
//    LaunchedEffect(Unit) {
//        viewModel.fetchScanResults(requestBody = )
//    }
//
//    scanResults?.let {
//        Text(text = "Scan Results: ${it.toString()}")
//    } ?: Text(text = "Loading...")
//}
