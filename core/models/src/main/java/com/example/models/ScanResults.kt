package com.example.models

import com.google.gson.Gson

data class ScanResults(
    val active_scanning: List<ActiveScanning>,
    val dns_enum: DnsEnum?,
    val network_footprinting: String?,
    val os_enum: OsEnum?,
    val service_info: ServiceInfo?,
    val subdomain_enum: List<SubdomainEnum>?
)

val jsonString = """ YOUR_JSON_HERE """
val scanResults = Gson().fromJson(jsonString, ScanResults::class.java)