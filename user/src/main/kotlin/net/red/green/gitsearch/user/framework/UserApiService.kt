package net.red.green.gitsearch.user.framework

import net.red.green.core.network.model.GenericResponse
import net.red.green.gitsearch.user.data.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {

    @GET("users?")
    suspend fun getSearchedGitUsers(@Query("q") query: String, @Query("page") page: Int): GenericResponse<UserResponse>
}
