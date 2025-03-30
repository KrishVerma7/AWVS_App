package com.example.awvs_app.presentation.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.awvs_app.R
import com.example.models.ActiveScanning
import com.example.models.DnsEnum
import com.example.models.OsEnum
import com.example.models.ScanResults
import com.example.models.ServiceInfo
import com.example.models.SubdomainEnum
import com.example.models.formatActiveScanning
import com.example.models.formatDnsEnum
import com.example.models.formatOsEnum
import com.example.models.formatServiceInfo
import com.example.models.formatSubdomainEnum

@Composable
fun DisplayScanResults(scanResults: ScanResults, targetUrl: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Ensures contrast with text
    ) {
        // Background Image with Overlay
        Image(
            painter = painterResource(id = R.drawable.auth),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f)) // Semi-transparent overlay
                .padding(12.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header Section
                item {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "SCAN RESULTS",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.poppins_regular))
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 8.dp)
                                .border(
                                    BorderStroke(1.dp, Color.Gray),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    append("Target URL : ")
                                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                                        append(targetUrl)
                                    }
                                },
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                modifier = Modifier.align(Alignment.CenterStart)
                            )
                        }
                    }
                }

                // Expandable Sections
                scanResults.dns_enum?.let { dnsEnum ->
                    item { ExpandableResultBox("DNS Enumeration", formatDnsEnum(dnsEnum)) }
                }

                scanResults.active_scanning?.takeIf { it.isNotEmpty() }?.let { activeScanning ->
                    item {
                        ExpandableResultBox(
                            title = "Active Scanning",
                            content = activeScanning.joinToString("\n\n") { formatActiveScanning(it) }
                        )
                    }
                }

                scanResults.os_enum?.let { osEnum ->
                    item { ExpandableResultBox("OS Enumeration", formatOsEnum(osEnum)) }
                }

                scanResults.service_info?.let { serviceInfo ->
                    item { ExpandableResultBox("Service Info", formatServiceInfo(serviceInfo)) }
                }

                scanResults.subdomain_enum?.takeIf { it.isNotEmpty() }?.let { subdomains ->
                    item {
                        ExpandableResultBox(
                            title = "Subdomain Enumeration",
                            content = subdomains.joinToString("\n") { formatSubdomainEnum(it) }
                        )
                    }
                }

                scanResults.network_footprinting?.let { networkFootprinting ->
                    item { ExpandableResultBox("Network Footprinting", networkFootprinting) }
                }
            }
        }
    }
}

@Composable
fun ExpandableResultBox(title: String, content: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E) // Dark Gray Theme
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins_regular))
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Expand",
                        tint = Color.White
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .background(Color.Black.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = content,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewScanResultsScreen() {
    DisplayScanResults(
        scanResults = ScanResults(
            active_scanning = listOf(
                ActiveScanning(
                    confidence = "High",
                    description = "Test Descriptiovvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvjghdfksjgsdkjfgnsdkfjgnkdfsjgnkdfjgndfkjgdfksjgbdfskjgdfkjgdfkjgnfdgnfdndfkgdfjghdfjgdfsgnsdfkjgnldfjgndfkjgndklgjbndn",
                    name = "Test Scan",
                    reference = "https://example.com",
                    risk = "Low",
                    solution = "Test Solution"
                ),
                ActiveScanning(
                    confidence = "High",
                    description = "Test Description",
                    name = "Test Scan",
                    reference = "https://example.com",
                    risk = "Low",
                    solution = "Test Solution"
                ),
                ActiveScanning(
                    confidence = "High",
                    description = "Test Description",
                    name = "Test Scan",
                    reference = "https://example.com",
                    risk = "Low",
                    solution = "Test Solution"
                )
            ),
            dns_enum = DnsEnum(
                A = "1.1.1.1",
                AAAA = "2606:4700:4700::1111",
                CNAME = null,
                SOA = null,
                SRV = null
            ),
            network_footprinting = "Test Network Footprinting",
            os_enum = OsEnum(
                deviceType = "Router",
                ipAddress = "192.168.1.1",
                openPorts = listOf("80", "443"),
                runningOs = "Linux",
                target = "TargetName"
            ),
            service_info = ServiceInfo(
                Host = "example.com",
                IP = "93.184.216.34",
                Open_Ports = listOf("443")
            ),
            subdomain_enum = listOf(
                SubdomainEnum(
                    content_length = "1024",
                    fuzz_word = "admin",
                    status_code = "200",
                    url = "https://sub.example.com"
                ),
                SubdomainEnum(
                    content_length = "1024",
                    fuzz_word = "admin",
                    status_code = "200",
                    url = "https://sub.example.com"
                ),
                SubdomainEnum(
                    content_length = "1024",
                    fuzz_word = "admin",
                    status_code = "200",
                    url = "https://sub.example.com"
                )
            )
        ),
        targetUrl = "target.url.com"
    )
}
