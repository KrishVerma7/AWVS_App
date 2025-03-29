package com.example.awvs_app.presentation.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.models.ScanResults
import com.example.models.formatActiveScanning
import com.example.models.formatDnsEnum
import com.example.models.formatOsEnum
import com.example.models.formatServiceInfo
import com.example.models.formatSubdomainEnum

@Composable
fun DisplayScanResults(scanResults: ScanResults) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        item { Text(text = "Scan Results:", fontSize = 20.sp, fontWeight = FontWeight.Bold) }

        scanResults.dns_enum?.let { dnsEnum ->
            item { ResultBox("DNS Enumeration", formatDnsEnum(dnsEnum)) }
        }

        scanResults.active_scanning?.let { activeScanning ->
            items(activeScanning.size) { index ->
                ResultBox("Active Scanning", formatActiveScanning(activeScanning[index]))
            }
        }

        scanResults.os_enum?.let { osEnum ->
            item { ResultBox("OS Enumeration", formatOsEnum(osEnum)) }
        }

        scanResults.service_info?.let { serviceInfo ->
            item { ResultBox("Service Info", formatServiceInfo(serviceInfo)) }
        }

        scanResults.subdomain_enum?.let { subdomains ->
            items(subdomains.size) { index ->
                ResultBox("Subdomain Enumeration", formatSubdomainEnum(subdomains[index]))
            }
        }
    }
}

@Composable
fun ResultBox(title: String, content: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Text(text = content, modifier = Modifier.padding(top = 4.dp))
        }
    }
}