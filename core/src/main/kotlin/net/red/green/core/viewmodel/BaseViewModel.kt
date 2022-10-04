package net.red.green.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    val showMessage: LiveData<String>
        get() = _showMessage
    protected val _showMessage = MutableLiveData<String>()
}
