package net.red.green.gitsearch.user.data

import net.red.green.core.network.model.GenericResponse

interface UserDataSource {

    suspend fun getSearchUserAccountList(query: String, page: Int) : GenericResponse<List<UserAccount>>
}
