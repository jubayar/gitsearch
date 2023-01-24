package net.red.green.gitsearch.user.framework

import net.red.green.core.network.NetworkModule
import net.red.green.core.network.model.BaseResponse
import net.red.green.core.network.model.GenericResponse
import net.red.green.gitsearch.user.data.UserDataSource
import net.red.green.gitsearch.user.data.UserAccount

class RemoteUserDataSource : UserDataSource {
    private val apiFactory = NetworkModule.provideApiFactory()
    private val userService = apiFactory.create(UserApiService::class.java)

    override suspend fun getSearchUserAccountList(query: String, page: Int): GenericResponse<List<UserAccount>> {

        return when(val data = userService.getSearchedGitUsers(query, page)) {
            is BaseResponse.Success -> BaseResponse.Success(data.body.items)
            is BaseResponse.ApiError -> BaseResponse.ApiError(data.errorBody, data.code)
            is BaseResponse.NetworkError -> BaseResponse.NetworkError(data.error)
            is BaseResponse.UnknownError -> BaseResponse.UnknownError(data.error)
        }
    }
}
