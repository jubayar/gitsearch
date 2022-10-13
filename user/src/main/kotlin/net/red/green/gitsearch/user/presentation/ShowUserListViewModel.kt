package net.red.green.gitsearch.user.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.red.green.core.usecase.ErrorRes
import net.red.green.core.usecase.UseCase
import net.red.green.core.viewmodel.BaseViewModel
import net.red.green.gitsearch.user.data.UserRepository
import net.red.green.gitsearch.user.domain.GetUserAccountsUseCase
import net.red.green.gitsearch.user.framework.RemoteUserDataSource
import net.red.green.gitsearch.user.presentation.model.QueryData
import net.red.green.gitsearch.user.presentation.model.UserAccountViewData

class ShowUserListViewModel : BaseViewModel() {
    private val _uiState = MutableStateFlow<AccountListUiState>(AccountListUiState.Loading(false))
    val uiState: StateFlow<AccountListUiState> = _uiState

    private val repo = UserRepository(RemoteUserDataSource())
    private val getUserAccountsUseCase = GetUserAccountsUseCase(repo)

    fun fetchUserAccountList(query: String, page: Int) {
        _uiState.value = AccountListUiState.Loading(true)
        val queryData = GetUserAccountsUseCase.RequestValues(QueryData())

        viewModelScope.launch(Dispatchers.IO) {
            getUserAccountsUseCase(queryData, object : UseCase.UseCaseCallback<GetUserAccountsUseCase.ResponseValue> {
                override fun onSuccessResponse(response: GetUserAccountsUseCase.ResponseValue, tag: String) {
                    _uiState.value = AccountListUiState.Loading(false)
                    _uiState.value = AccountListUiState.Success(response.userAccounts)
                }

                override fun onErrorResponse(errorRes: ErrorRes, tag: String) {
                    _uiState.value = AccountListUiState.Loading(false)
                    _uiState.value = AccountListUiState.Error(errorRes)
                }
            })
        }
    }

    sealed class AccountListUiState {
        class Loading(val flag: Boolean) : AccountListUiState()
        class Success(val accounts: List<UserAccountViewData>) : AccountListUiState()
        class Error(val errorRes: ErrorRes) : AccountListUiState()
    }
}

