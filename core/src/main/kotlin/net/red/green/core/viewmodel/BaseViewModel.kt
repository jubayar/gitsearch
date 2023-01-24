package net.red.green.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.red.green.core.usecase.ErrorRes
import net.red.green.core.usecase.UseCase
import net.red.green.core.usecase.UseCaseResponse

abstract class BaseViewModel : ViewModel() {
    val showMessage: LiveData<String>
        get() = _showMessage
    protected val _showMessage = MutableLiveData<String>()

    protected fun executeSuspendedCodeBlock(
        useCase: String = String(),
        useCaseBlock: suspend () -> UseCaseResponse<UseCase.ResponseValue, ErrorRes>
    ) {
        viewModelScope.launch {
            when(val data = useCaseBlock()) {
                is UseCaseResponse.Success -> {
                    onSuccessResponse(useCase, data.body)
                }

                is UseCaseResponse.Error -> {
                    onErrorResponse(useCase, data.errorBody)
                }
            }
        }
    }

    abstract fun onSuccessResponse(useCase: String, response: UseCase.ResponseValue)
    abstract fun onErrorResponse(useCase: String, errorRes: ErrorRes)
}
