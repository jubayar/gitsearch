package net.red.green.core.usecase

sealed class UseCaseResponse<out T : Any, out E : Any> {

    data class Success<S: UseCase.ResponseValue>(val body: S): UseCaseResponse<S, Nothing>()

    data class Error(val errorBody: ErrorRes) : UseCaseResponse<Nothing, ErrorRes>()
}
