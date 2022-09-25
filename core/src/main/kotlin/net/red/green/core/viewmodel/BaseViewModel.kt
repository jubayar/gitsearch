package net.red.green.core.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    val dataLoading = ObservableBoolean(false)

    val showMessage: LiveData<String>
        get() = _showMessage
    protected val _showMessage = MutableLiveData<String>()
}
