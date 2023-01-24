package net.red.green.gitsearch.user.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.red.green.core.usecase.UseCase
import net.red.green.core.network.model.BaseResponse
import net.red.green.core.usecase.ErrorRes
import net.red.green.core.usecase.UseCaseResponse
import net.red.green.gitsearch.user.data.UserRepository
import net.red.green.gitsearch.user.domain.mapper.GitUsersViewDataMapper
import net.red.green.gitsearch.user.presentation.model.QueryData
import net.red.green.gitsearch.user.presentation.model.UserAccountViewData

class GetUserAccountsUseCase(
    private val userRepository: UserRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : UseCase<GetUserAccountsUseCase.RequestValues, GetUserAccountsUseCase.ResponseValue>() {

    override suspend fun invoke(
        requestValues: RequestValues?
    ): UseCaseResponse<UseCase.ResponseValue, ErrorRes> {

        withContext(defaultDispatcher) {
            when (val result = userRepository.getUserAccountList(
                requestValues!!.queryData.query,
                requestValues.queryData.page
            )) {

                is BaseResponse.Success -> {
                    val accountList = result.body
                    val accountViewList = GitUsersViewDataMapper.mapToViewData(accountList)
                    UseCaseResponse.Success(ResponseValue(accountViewList))
                }

                is BaseResponse.ApiError -> UseCaseResponse.Error(
                    ErrorRes(
                        code = result.code,
                        errorMsgList = listOf(result.errorBody.throwable.message!!)
                    )
                )

                is BaseResponse.NetworkError ->
                    UseCaseResponse.Error(ErrorRes(errorMsgList = listOf(result.error.message!!)))

                is BaseResponse.UnknownError ->
                    UseCaseResponse.Error(ErrorRes(errorMsgList = listOf(result.error?.message!!)))
            }
        }

        return UseCaseResponse.Error(ErrorRes(errorMsgList = listOf("Something wrong")))
    }

    class RequestValues(val queryData: QueryData) : UseCase.RequestValues
    class ResponseValue(val userAccounts: List<UserAccountViewData>) : UseCase.ResponseValue

    companion object {
        val useCaseName = GetUserAccountsUseCase::class.java.simpleName
    }
}
