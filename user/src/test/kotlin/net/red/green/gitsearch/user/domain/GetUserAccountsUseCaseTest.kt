package net.red.green.gitsearch.user.domain

import kotlinx.coroutines.test.runTest
import net.red.green.core.network.model.BaseResponse
import net.red.green.core.usecase.UseCaseResponse
import net.red.green.gitsearch.user.data.UserAccount
import net.red.green.gitsearch.user.data.UserRepository
import net.red.green.gitsearch.user.domain.mapper.GitUsersViewDataMapper
import net.red.green.gitsearch.user.presentation.model.QueryData
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class GetUserAccountsUseCaseTest {
    private val mockUserRepository: UserRepository = Mockito.mock(UserRepository::class.java)
    private val query = "a"
    private val page = 1
    private val queryData = QueryData(query, page)
    private val userAccounts = listOf(
        UserAccount(login = "aaa", avatar_url = "avatar_url", url = "url")
    )
    private val searchedUsersViewData = GitUsersViewDataMapper.mapToViewData(userAccounts)
    private val useCase = GetUserAccountsUseCase(mockUserRepository)

    @Test
    fun `verify getUserAccountList() is invoked and domain data is mapped to view data`() = runTest {
        Mockito.`when`(mockUserRepository.getUserAccountList(query, page)).thenReturn(
            BaseResponse.Success(
            userAccounts
        ))

        val data = useCase(GetUserAccountsUseCase.RequestValues(queryData))

        Mockito.verify(mockUserRepository).getUserAccountList(query, page)
        Assert.assertEquals((data as UseCaseResponse.Success<GetUserAccountsUseCase.ResponseValue>).body.userAccounts, searchedUsersViewData)
    }
}
