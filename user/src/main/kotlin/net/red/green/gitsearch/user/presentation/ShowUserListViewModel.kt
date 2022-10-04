package net.red.green.gitsearch.user.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.red.green.core.UseCase
import net.red.green.core.viewmodel.BaseViewModel
import net.red.green.gitsearch.user.data.UserRepository
import net.red.green.gitsearch.user.domain.GetUserAccounts
import net.red.green.gitsearch.user.framework.RemoteUserDataSource
import net.red.green.gitsearch.user.presentation.model.QueryData
import net.red.green.gitsearch.user.presentation.model.UserAccountViewData

class ShowUserListViewModel : BaseViewModel(), UseCase.UseCaseCallback<GetUserAccounts.ResponseValue> {
    private val repo = UserRepository(RemoteUserDataSource())
    private val getUserAccountsUseCase = GetUserAccounts(repo)

    val listUserAccount = MutableLiveData<List<UserAccountViewData>>()

    fun fetchUserAccountList(query: String, page: Int) {
        val queryData = GetUserAccounts.RequestValues(QueryData())

        viewModelScope.launch {
            getUserAccountsUseCase.execute(queryData, this@ShowUserListViewModel)
        }
    }

    override fun onSuccessResponse(response: GetUserAccounts.ResponseValue, tag: String) {
        listUserAccount.value = response.userAccounts
    }

    override fun onApiError(throwable: Throwable, code: Int, tag: String) {
        throwable.message
    }

    override fun onNetworkError(throwable: Throwable, tag: String) {
        throwable.message
    }

      override fun onUnknownError(throwable: Throwable?, tag: String) {
        throwable?.message
    }
}
