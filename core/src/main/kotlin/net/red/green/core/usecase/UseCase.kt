package net.red.green.core.usecase

abstract class UseCase<Q : UseCase.RequestValues, P : UseCase.ResponseValue> {
    var requestValues: Q? = null
    var useCaseCallback: UseCaseCallback<P>? = null

    abstract suspend operator fun invoke(requestValues: Q?, callback: UseCaseCallback<P>)

    interface RequestValues
    interface ResponseValue

    interface UseCaseCallback<ResponseValue> {
        fun onSuccessResponse(response: ResponseValue)
        fun onErrorResponse(errorRes: ErrorRes)
    }
}
