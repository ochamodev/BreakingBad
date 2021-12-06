package org.ochamo.breakingbad.data



data class DataResult<out T>(
    val success: T?,
    val error: Exception?
)