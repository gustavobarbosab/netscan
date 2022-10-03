package com.network.scanner

import androidx.lifecycle.MutableLiveData
import com.network.scanner.common.SingleLiveEvent

class MainState {

    val state = MutableLiveData<ViewState>()
    val action = SingleLiveEvent<ActionState>()

    sealed class ViewState {
    }

    sealed class ActionState {
        class UpdateScreen(val value: String, val time: Long) : ActionState()
    }
}