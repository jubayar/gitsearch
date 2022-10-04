package net.red.green.core

abstract class UseCase<Q : UseCase.RequestValues, P : UseCase.ResponseValue> {
    var requestValues: Q? = null
    var useCaseCallback: UseCaseCallback<P>? = null

    abstract suspend operator fun invoke(requestValues: Q?, callback: UseCaseCallback<P>)

    interface RequestValues
    interface ResponseValue

    interface UseCaseCallback<R> {
        fun onSuccessResponse(response: R, tag: String = String())
        fun onApiError(throwable: Throwable, code: Int, tag: String = String())
        fun onNetworkError(throwable: Throwable, tag: String = String())
        fun onUnknownError(throwable: Throwable?, tag: String = String())
    }
}
