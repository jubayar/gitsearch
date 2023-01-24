package net.red.green.core.usecase

abstract class UseCase<Q : UseCase.RequestValues, P : UseCase.ResponseValue> {
    var requestValues: Q? = null

    abstract suspend operator fun invoke(requestValues: Q?) : UseCaseResponse<ResponseValue, ErrorRes>

    interface RequestValues
    interface ResponseValue
}
