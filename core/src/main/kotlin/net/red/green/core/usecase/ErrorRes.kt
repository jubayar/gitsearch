package net.red.green.core.usecase

data class ErrorRes(
    val code: Int? = null,
    val errorType: String? = null,
    val errorMsgList: List<String>
)
