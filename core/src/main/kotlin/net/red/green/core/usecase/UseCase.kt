package net.red.green.core.usecase

abstract class UseCase<Q : UseCase.RequestValues, P : UseCase.ResponseValue> {
    var requestValues: Q? = null
    var useCaseCallback: UseCaseCallback<P>? = null

    abstract suspend operator fun invoke(requestValues: Q?, callback: UseCaseCallback<P>)

    interface RequestValues
    interface ResponseValue

    interface UseCaseCallback<R> {
        fun onSuccessResponse(response: R, tag: String = String())
        fun onErrorResponse(errorRes: ErrorRes, tag: String = String())
    }
}
