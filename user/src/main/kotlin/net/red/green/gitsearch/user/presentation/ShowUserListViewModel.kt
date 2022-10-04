package net.red.green.gitsearch.user.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.red.green.core.UseCase
import net.red.green.core.viewmodel.BaseViewModel
import net.red.green.gitsearch.user.data.UserRepository
import net.red.green.gitsearch.user.domain.GetUserAccountsUseCase
import net.red.green.gitsearch.user.framework.RemoteUserDataSource
import net.red.green.gitsearch.user.presentation.model.QueryData
import net.red.green.gitsearch.user.presentation.model.UserAccountViewData

class ShowUserListViewModel : BaseViewModel(), UseCase.UseCaseCallback<GetUserAccountsUseCase.ResponseValue> {
    private val repo = UserRepository(RemoteUserDataSource())
    private val getUserAccountsUseCase = GetUserAccountsUseCase(repo)

    val listUserAccount = MutableLiveData<List<UserAccountViewData>>()

    fun fetchUserAccountList(query: String, page: Int) {
        val queryData = GetUserAccountsUseCase.RequestValues(QueryData())

        viewModelScope.launch {
            getUserAccountsUseCase(queryData, this@ShowUserListViewModel)
        }
    }

    override fun onSuccessResponse(response: GetUserAccountsUseCase.ResponseValue, tag: String) {
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
