package net.red.green.gitsearch.user.data

import net.red.green.core.network.model.GenericResponse
import net.red.green.gitsearch.user.domain.UserAccount

interface UserDataSource {

    suspend fun getSearchUserAccountList(query: String, page: Int) : GenericResponse<List<UserAccount>>
}
