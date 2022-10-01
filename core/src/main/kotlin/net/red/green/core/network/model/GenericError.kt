package net.red.green.core.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenericError(
    val code: String,
    val throwable: Throwable
)
