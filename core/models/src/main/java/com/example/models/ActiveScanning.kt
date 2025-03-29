package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class ActiveScanning(
    val confidence: String,
    val description: String,
    val name: String,
    val reference: String,
    val risk: String,
    val solution: String
)

fun formatActiveScanning(scan: ActiveScanning): String = buildString {
    appendLine("Name: ${scan.name}")
    appendLine("Confidence: ${scan.confidence}")
    appendLine("Risk: ${scan.risk}")
    appendLine("Solution: ${scan.solution}")
    appendLine("Description: ${scan.description}")
    appendLine("Reference: ${scan.reference}")
}
