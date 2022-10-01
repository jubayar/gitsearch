package net.red.green.gitsearch.user.data

class UserRepository(private val dataSource: UserDataSource) {

    suspend fun getUserAccountList(query: String, page: Int) =
        dataSource.getSearchUserAccountList(query, page)
}
