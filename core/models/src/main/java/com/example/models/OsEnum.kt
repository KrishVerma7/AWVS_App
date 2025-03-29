package com.example.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class OsEnum(
    @SerializedName("Device Type") val deviceType: String,
    @SerializedName("IP Address") val ipAddress: String,
    @SerializedName("Open Ports") val openPorts: List<String>,
    @SerializedName("Running OS") val runningOs: String,
    val target: String
)

fun formatOsEnum(osEnum: OsEnum): String = buildString {
    appendLine("Device Type: ${osEnum.deviceType}")
    appendLine("IP Address: ${osEnum.ipAddress}")
    appendLine("Running OS: ${osEnum.runningOs}")
    appendLine("Target: ${osEnum.target}")
    appendLine("Open Ports: ${osEnum.openPorts.joinToString(", ")}")
}
