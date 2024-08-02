package com.example.dog_app.page

import androidx.lifecycle.MutableLiveData
import com.example.dog_app.viewmodel.ObservableViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : ObservableViewModel() {
    val showLoadingView = MutableLiveData<Boolean>(false)
    val showSendButtonView = MutableLiveData<Boolean>(false)

    fun unsubscribe() {
        clearAllCalls()
    }
}