package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class ServiceInfo(
    val Host: String,
    val IP: String,
    val Open_Ports: List<String>
)

fun formatServiceInfo(serviceInfo: ServiceInfo): String = buildString {
    appendLine("Host: ${serviceInfo.Host}")
    appendLine("IP: ${serviceInfo.IP}")
    appendLine("Open Ports: ${serviceInfo.Open_Ports.joinToString(", ")}")
}
