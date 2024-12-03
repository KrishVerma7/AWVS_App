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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.awvs_app.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val activity = LocalContext.current as? Activity

    BackHandler {
        activity?.finish()
    }

    var url by remember { mutableStateOf("") }
    var optionsState = remember {
        mutableStateOf(
            listOf(
                "OS Enumeration" to true,
                "Service Information" to true,
                "Subdomain Enumeration" to true,
                "DNS Enumeration" to true,
                "Email Footprinting" to true,
                "Network Footprinting" to true
            )
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.auth),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.2f),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 50.dp, start = 16.dp, end = 16.dp),
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
                        .clickable(onClick = {
                            navController.navigate("profile")
                        }),

                    )
            }

            // URL Input
            Surface(
                border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = url,
                    onValueChange = { url = it },
                    placeholder = {
                        Text(
                            text = "https://www.target.com/",
                            color = Color.Gray,
                            fontFamily = FontFamily(Font(R.font.poppins_regular))
                            )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_search_24),
                            contentDescription = null
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }

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
                            Text(
                                text = option.first,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontFamily = FontFamily(Font(R.font.poppins_regular))
                            )
                            Switch(
                                checked = option.second,
                                onCheckedChange = { checked ->
                                    optionsState.value = optionsState.value.toMutableList().apply {
                                        set(index, option.first to checked)
                                    }
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                                    checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                                    uncheckedTrackColor = MaterialTheme.colorScheme.secondary.copy(
                                        alpha = 0.5f
                                    )
                                )
                            )
                        }
                    }
                }
            }

            // Scan Results Button
            Button(
                onClick = { /* Handle scan results */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .align(Alignment.End)
                    .clip(RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Scan Results",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins_regular))
                )
            }
        }
    }
}