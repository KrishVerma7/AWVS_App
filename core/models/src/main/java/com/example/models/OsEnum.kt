package com.example.models

import com.google.gson.annotations.SerializedName

data class OsEnum(
    @SerializedName("Device Type") val deviceType: String,
    @SerializedName("IP Address") val ipAddress: String,
    @SerializedName("Open Ports") val openPorts: List<String>,
    @SerializedName("Running OS") val runningOs: String,
    val target: String
)

