package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class ScanResults(
    val active_scanning: List<ActiveScanning>,
    val dns_enum: DnsEnum?,
    val network_footprinting: String?,
    val os_enum: OsEnum?,
    val service_info: ServiceInfo?,
    val subdomain_enum: List<SubdomainEnum>?
)