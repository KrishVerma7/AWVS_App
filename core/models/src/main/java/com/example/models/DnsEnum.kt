package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class DnsEnum(
    val A: String?,
    val AAAA: String?,
    val CNAME: String?,
    val SOA: String?,
    val SRV: String?
)

fun formatDnsEnum(dnsEnum: DnsEnum): String = buildString {
    dnsEnum.A?.let { appendLine("A Record: $it") }
    dnsEnum.AAAA?.let { appendLine("AAAA Record: $it") }
    dnsEnum.CNAME?.let { appendLine("CNAME Record: $it") }
    dnsEnum.SOA?.let { appendLine("SOA Record: $it") }
    dnsEnum.SRV?.let { appendLine("SRV Record: $it") }
}