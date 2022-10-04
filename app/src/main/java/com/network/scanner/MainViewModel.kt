package com.network.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network.scanner.MainState.ActionState.*
import com.network.scanner.core.scanner.tools.ping.PingDevice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val mainState = MainState()
    private var screen = ""

    val viewAction
        get() = mainState.action

    fun pingDevice() {
        viewModelScope.launch(Dispatchers.Main) {
            if (repository.isWifiConnected().not()) {
                viewAction.value = UpdateScreen("Não há conexão Wifi", 0)
                return@launch
            }

//            repeat(5) { time ->
//                val pingResponse = repository.pingDevice("192.168.100.1")
//
//                screen += "\n" + when (pingResponse) {
//                    is PingDevice.PingResponse.Success -> "from:${pingResponse.origin} --- time:${pingResponse.time}"
//                    PingDevice.PingResponse.TimeOut -> "Ping request timeout"
//                }
//                viewAction.value = UpdateScreen(screen, time * 1000L)
//            }
            val devices = repository.findDevices()
            devices.forEach {
                screen += it.ip + "\n"
            }
            viewAction.value = UpdateScreen(screen, 1000)
        }
    }


}