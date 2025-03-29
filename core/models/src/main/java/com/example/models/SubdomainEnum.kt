package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class SubdomainEnum(
    val content_length: String,
    val fuzz_word: String?,
    val status_code: String,
    val url: String
)

fun formatSubdomainEnum(subdomain: SubdomainEnum): String = buildString {
    appendLine("Content Length: ${subdomain.content_length}")
    subdomain.fuzz_word?.let { appendLine("Fuzz Word: $it") }
    appendLine("Status Code: ${subdomain.status_code}")
    appendLine("URL: ${subdomain.url}")
}