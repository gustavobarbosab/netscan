package com.network.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val mainState = MainState()

    val viewAction
        get() = mainState.action

    fun pingDevice() {
        viewModelScope.launch(Dispatchers.Main) {

        }
    }
}