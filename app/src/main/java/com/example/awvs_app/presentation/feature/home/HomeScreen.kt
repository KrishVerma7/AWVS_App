package com.example.awvs_app.presentation.feature.home

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.awvs_app.R
import com.example.awvs_app.ScanResultsViewModel
import com.example.data.repository.sign_in.RetrofitClient


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController,scanResultsViewModel: ScanResultsViewModel) {
    val activity = LocalContext.current as? Activity ?: return
    val context = LocalContext.current
    val retrofitClient = remember { RetrofitClient }

    BackHandler {
        activity?.finish()
    }

    var targetUrl by remember { mutableStateOf("") }
    var serverIp by remember { mutableStateOf("") }
    var optionsState = remember {
        mutableStateOf(
            listOf(
                "OS Enumeration" to true,
                "Service Information" to true,
                "Subdomain Enumeration" to true,
                "DNS Enumeration" to true,
                "Active Scanning" to true,
                "Network Footprinting" to true
            )
        )
    }

//    var scanResults by remember { mutableStateOf<ScanResults?>(null) }
//    var isLoading by remember { mutableStateOf(false) }
//    var errorMessage by remember { mutableStateOf<String?>(null) }
//    val scanResults by scanResultsViewModel.scanResults

//    val scanResults by scanResultsViewModel.scanResults
    val isLoading by scanResultsViewModel.isLoading
    val errorMessage by scanResultsViewModel.errorMessage

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.auth),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().alpha(0.2f),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 50.dp, start = 16.dp, end = 16.dp),
//                .verticalScroll(rememberScrollState()), // Add scrollable behavior ,

            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_menu_24),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )

                Icon(
                    painter = painterResource(R.drawable.baseline_person_pin_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { navController.navigate("profile") }
                )
            }

            // URL Input
            InputField(
                label = "Target URL",
                value = targetUrl,
                onValueChange = { targetUrl = it }
            )

            // Server IP Input
            InputField(
                label = "Server IP",
                value = serverIp,
                onValueChange = { serverIp = it }
            )

            // Scanning Options Header
            Text(
                text = "Scanning Options",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            )

            // Scanning Options
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                optionsState.value.forEachIndexed { index, option ->
                    OptionSwitch(
                        label = option.first,
                        isChecked = option.second,
                        onToggle = { checked ->
                            optionsState.value = optionsState.value.toMutableList().apply {
                                set(index, option.first to checked)
                            }
                        }
                    )
                }
            }

            // Scan Results Button
            Button(
                onClick = {
                    if (targetUrl.isBlank() || serverIp.isBlank()) {
                        Toast.makeText(
                            activity,
                            "Please enter both URL and Server IP",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    val formattedUrl = if (!serverIp.startsWith("http://") && !serverIp.startsWith("https://")) {
                        "https://$serverIp"
                    } else {
                        serverIp
                    }

//                    isLoading = true
//                    errorMessage = null
//                    scanResults = null

                    val requestBody = mapOf(
                        "target" to targetUrl,
                        "os_enum" to optionsState.value.first { it.first == "OS Enumeration" }.second,
                        "service_info" to optionsState.value.first { it.first == "Service Information" }.second,
                        "subdomain_enum" to optionsState.value.first { it.first == "Subdomain Enumeration" }.second,
                        "dns_enum" to optionsState.value.first { it.first == "DNS Enumeration" }.second,
                        "active_scanning" to optionsState.value.first { it.first == "Active Scanning" }.second,
                        "network_footprinting" to optionsState.value.first { it.first == "Network Footprinting" }.second
                    )
                    val apiService = retrofitClient.createApiService(formattedUrl)
                    scanResultsViewModel.fetchScanResults(apiService,requestBody,activity)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Scan Results")
            }

            // Loading State
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            }

            // Display Scan Results
//            scanResults?.let { results ->
//                DisplayScanResults(results)
//            }


            // Error State
//            errorMessage?.value?.let {
//                Text(
//                    text = it,
//                    color = Color.Red,
//                    modifier = Modifier.padding(8.dp)
//                )
//                Button(onClick = {  scanResultsViewModel.fetchScanResults(mapOf()) }) {
//                    Text("Retry")
//                }
//            }
        }
    }
}

@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Surface(
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = label, color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(),
            singleLine = true,
        )
    }
}

@Composable
fun OptionSwitch(label: String, isChecked: Boolean, onToggle: (Boolean) -> Unit) {
    Surface(
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontSize = 16.sp)
            Switch(
                checked = isChecked,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                    uncheckedTrackColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                )
            )
        }
    }
}

//@Preview
//@Composable
//private fun PreviewHomeScreen() {
//    AWVS_AppTheme {
//        HomeScreen(navController = rememberNavController())
//    }
//}