package com.example.awvs_app.presentation.feature.home

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.awvs_app.R
import com.example.awvs_app.ScanResultsViewModel
import com.example.awvs_app.ui.theme.AWVS_AppTheme
import com.example.awvs_app.ui.theme.poppinsFontFamily
import com.example.data.repository.sign_in.RetrofitClient


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, scanResultsViewModel: ScanResultsViewModel) {
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
            ).map { it.first to it.second as Boolean? }
        )
    }

    val isLoading by scanResultsViewModel.isLoading

    Box(modifier = Modifier.fillMaxSize().padding(vertical = 12.dp)) {
        Image(
            painter = painterResource(id = R.drawable.auth),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.15f),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 32.dp, start = 12.dp, end = 12.dp), // Slightly increased padding
            verticalArrangement = Arrangement.spacedBy(16.dp), // Adjusted spacing
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.baseline_menu_24),
//                    contentDescription = null,
//                    modifier = Modifier.size(28.dp) // Slightly increased
//                )
//
//                Icon(
//                    painter = painterResource(R.drawable.baseline_person_pin_24),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(28.dp)
//                        .clickable { navController.navigate("profile") }
//                )
//            }

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
                fontSize = 18.sp, // Slightly reduced from 20.sp
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = poppinsFontFamily
            )

            // Scanning Options List
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp) // Balanced spacing
            ) {
                itemsIndexed(optionsState.value) { index, option ->
                    OptionSwitch(
                        label = option.first,
                        isChecked = option.second?:false,
                        onToggle = { checked ->
                            optionsState.value = optionsState.value.toMutableList().apply {
                                set(index, option.first to checked)
                            }
                        }
                    )
                }

                item {
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
                            val formattedUrl =
                                if (!serverIp.startsWith("http://") && !serverIp.startsWith("https://")) {
                                    "https://$serverIp"
                                } else {
                                    serverIp
                                }

                            val requestBody: Map<String, Any?> = mapOf(
                                "target" to targetUrl,
                                "os_enum" to if (optionsState.value[0].second == true) true else null,
                                "service_info" to if (optionsState.value[1].second == true) true else null,
                                "subdomain_enum" to if (optionsState.value[2].second == true) true else null,
                                "dns_enum" to if (optionsState.value[3].second == true) true else null,
                                "active_scanning" to if (optionsState.value[4].second == true) true else null,
                                "network_footprinting" to if (optionsState.value[5].second == true) true else null
                            )

                            val apiService = retrofitClient.createApiService(formattedUrl)
                            scanResultsViewModel.fetchScanResults(apiService,
                                requestBody as Map<String, @JvmSuppressWildcards Any?>, activity, targetUrl)

                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, // Transparent background
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
                        shape = RoundedCornerShape(24.dp) // Reduced from 28.dp for better proportion
                    ) {
                        Text("Scan Results", fontFamily = poppinsFontFamily, fontSize = 14.sp) // Reduced slightly
                    }
                }
            }

            // Loading State
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp)) // Adjusted padding
            }
        }
    }
}

@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Surface(
        border = BorderStroke(0.5.dp, Color.Gray.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = label,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily(Font(R.font.poppins_regular))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(),
            singleLine = true,
            textStyle = TextStyle(fontSize = 14.sp)
        )
    }
}

@Composable
fun OptionSwitch(label: String, isChecked: Boolean, onToggle: (Boolean?) -> Unit) {
    Surface(
        border = BorderStroke(0.5.dp, Color.Gray.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            )
            Switch(
                checked = isChecked ?: false, // Ensure checked is always Boolean
                onCheckedChange = { checked -> onToggle(if (checked) true else null) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Green,
                    checkedTrackColor = Color.Green.copy(alpha = 0.2f),
                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                    uncheckedTrackColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                ),
                modifier = Modifier.size(20.dp).padding(end = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    AWVS_AppTheme {
        HomeScreen(
            navController = rememberNavController(),
            scanResultsViewModel = ScanResultsViewModel()
        )
    }
}
