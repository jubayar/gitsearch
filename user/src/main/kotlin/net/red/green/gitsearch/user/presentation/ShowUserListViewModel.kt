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

class ShowUserListViewModel : BaseViewModel() {
    private val repo = UserRepository(RemoteUserDataSource())
    private val getUserAccounts = GetUserAccounts(repo)

    val listUserAccount = MutableLiveData<List<UserAccountViewData>>()

    fun fetchUserAccountList(query: String, page: Int) {
        val queryData = GetUserAccounts.RequestValues(QueryData())

        viewModelScope.launch {
            getUserAccounts.execute(queryData, object: UseCase.UseCaseCallback<GetUserAccounts.ResponseValue> {
                override fun onSuccessResponse(response: GetUserAccounts.ResponseValue) {
                    listUserAccount.value = response.userAccounts
                }

                override fun onApiError(throwable: Throwable, code: Int) {
                    throwable.message
                }

                override fun onNetworkError(throwable: Throwable) {
                    throwable.message
                }

                override fun onUnknownError(throwable: Throwable?) {
                    throwable?.message
                }
            })
        }
    }
}
