package net.red.green.gitsearch.user.domain

import net.red.green.core.UseCase
import net.red.green.core.network.model.BaseResponse
import net.red.green.gitsearch.user.data.UserRepository
import net.red.green.gitsearch.user.domain.mapper.GitUsersViewDataMapper
import net.red.green.gitsearch.user.presentation.model.QueryData
import net.red.green.gitsearch.user.presentation.model.UserAccountViewData

class GetUserAccounts(private val userRepository: UserRepository) : UseCase<GetUserAccounts.RequestValues, GetUserAccounts.ResponseValue>() {

    override suspend fun execute(requestValues: RequestValues?, callback: UseCaseCallback<ResponseValue>) {

        when(val result = userRepository.getUserAccountList(requestValues!!.queryData.query, requestValues.queryData.page)) {

            is BaseResponse.Success -> {
                val accountList = result.body
                val accountViewList = GitUsersViewDataMapper.mapToViewData(accountList)
                callback.onSuccessResponse(ResponseValue(accountViewList))
            }

            is BaseResponse.ApiError -> callback.onApiError(result.errorBody.throwable, result.code)

            is BaseResponse.NetworkError -> callback.onNetworkError(result.error)

            is BaseResponse.UnknownError -> callback.onUnknownError(result.error)
        }
    }

    class RequestValues(val queryData: QueryData) : UseCase.RequestValues
    class ResponseValue(val userAccounts : List<UserAccountViewData>) : UseCase.ResponseValue
}
