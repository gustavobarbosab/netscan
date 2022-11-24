package com.network.scanner

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network.scanner.MainState.ActionState.UpdateScreen
import com.network.scanner.core.scanner.model.NetScanScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val mainState = MainState()

    val viewAction
        get() = mainState.action

    fun pingDevice() {
        viewModelScope.launch(Dispatchers.Main) {
            if (repository.isWifiConnected().not()) {
                viewAction.value = UpdateScreen("Não há conexão Wifi", 0)
                return@launch
            }

            repeat(5) {
                repository.pingDevice("https://www.google.com")
                    .onScheduler(NetScanScheduler.Main)
                    .onSuccess {
                        Log.e("Sucesso","Sucesso...")
                        viewAction.value = UpdateScreen("Success!", 1000)
                    }
                    .onError {
                        Log.e("Erro","Erro...")
                        viewAction.value = UpdateScreen("Error!", 1000)
                    }
            }
        }
    }
}