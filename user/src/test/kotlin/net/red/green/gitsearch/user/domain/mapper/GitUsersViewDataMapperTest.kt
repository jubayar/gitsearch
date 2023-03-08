package net.red.green.gitsearch.user.domain.mapper

import net.red.green.gitsearch.user.data.UserAccount
import net.red.green.gitsearch.user.presentation.model.UserAccountViewData
import org.junit.Assert
import org.junit.Test

class GitUsersViewDataMapperTest {
    private val userAccounts = listOf<UserAccount>(
        UserAccount(login = "aaa", avatar_url = "avatar_url", url = "url")
    )

    private val expectedResult =
        UserAccountViewData(
            login = "aaa",
            avatar_url = "avatar_url",
            url = "url"
        )

    @Test
    fun mapToViewData() {
        val actualResult = GitUsersViewDataMapper.mapToViewData(userAccounts)

        Assert.assertEquals(expectedResult.login, actualResult[0].login)
        Assert.assertEquals(expectedResult.avatar_url, actualResult[0].avatar_url)
        Assert.assertEquals(expectedResult.url, actualResult[0].url)
    }
}
