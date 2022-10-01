package net.red.green.core

abstract class UseCase<Q : UseCase.RequestValues, P : UseCase.ResponseValue> {

    var requestValues: Q? = null

    var useCaseCallback: UseCaseCallback<P>? = null

    abstract suspend fun execute(requestValues: Q?, callback: UseCaseCallback<P>)

    interface RequestValues

    interface ResponseValue

    interface UseCaseCallback<R> {
        fun onSuccessResponse(response: R)
        fun onApiError(throwable: Throwable, code: Int)
        fun onNetworkError(throwable: Throwable)
        fun onUnknownError(throwable: Throwable?)
    }
}
