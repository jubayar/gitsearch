package net.red.green.gitsearch.user.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.red.green.core.usecase.UseCase
import net.red.green.core.network.model.BaseResponse
import net.red.green.core.usecase.ErrorRes
import net.red.green.gitsearch.user.data.UserRepository
import net.red.green.gitsearch.user.domain.mapper.GitUsersViewDataMapper
import net.red.green.gitsearch.user.presentation.model.QueryData
import net.red.green.gitsearch.user.presentation.model.UserAccountViewData

class GetUserAccountsUseCase(
    private val userRepository: UserRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : UseCase<GetUserAccountsUseCase.RequestValues, GetUserAccountsUseCase.ResponseValue>() {

    override suspend fun invoke(
        requestValues: RequestValues?,
        callback: UseCaseCallback<ResponseValue>
    ) {
        withContext(defaultDispatcher) {
            when (val result = userRepository.getUserAccountList(
                requestValues!!.queryData.query,
                requestValues.queryData.page
            )) {

                is BaseResponse.Success -> {
                    val accountList = result.body
                    val accountViewList = GitUsersViewDataMapper.mapToViewData(accountList)
                    callback.onSuccessResponse(ResponseValue(accountViewList))
                }

                is BaseResponse.ApiError -> callback.onErrorResponse(
                    ErrorRes(code = result.code, errorMsgList = listOf(result.errorBody.throwable.message!!))
                )

                is BaseResponse.NetworkError -> callback.onErrorResponse(
                    ErrorRes(errorMsgList = listOf(result.error.message!!))
                )

                is BaseResponse.UnknownError -> callback.onErrorResponse(
                    ErrorRes(errorMsgList = listOf(result.error?.message!!))
                )
            }
        }
    }

    class RequestValues(val queryData: QueryData) : UseCase.RequestValues
    class ResponseValue(val userAccounts: List<UserAccountViewData>) : UseCase.ResponseValue
}
