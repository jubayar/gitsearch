package net.red.green.gitsearch.user.domain.mapper

import net.red.green.gitsearch.user.domain.UserAccount
import net.red.green.gitsearch.user.presentation.model.UserAccountViewData

internal object GitUsersViewDataMapper {

    fun mapToViewData(searchedUsers: List<UserAccount>): List<UserAccountViewData> {
        val list = mutableListOf<UserAccountViewData>()
        searchedUsers.let { it ->
            it.forEach {
                list.add(
                    UserAccountViewData(
                        login = it.login.orEmpty(),
                        avatar_url = it.avatar_url.orEmpty(),
                        url = it.url.orEmpty()
                    )
                )
            }
            return list
        }
    }
}
