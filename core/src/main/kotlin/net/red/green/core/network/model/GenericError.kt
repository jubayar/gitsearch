package net.red.green.core.network.model

data class GenericError(
    val code: String,
    val throwable: Throwable,
    val messages: List<String>
)
