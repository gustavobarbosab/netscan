package com.network.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network.scanner.MainState.ActionState.*
import com.network.scanner.core.scanner.tools.ping.PingDevice
import com.network.scanner.core.scanner.tools.ping.PingDevice.PingResponse.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val mainState = MainState()
    private var screen = ""

    val viewAction
        get() = mainState.action

    fun pingDevice() {
        viewModelScope.launch(Dispatchers.Main) {
            val pingList = repository.pingDevice("192.168.100.100")
            pingList.forEachIndexed { time, pingResponse ->
                screen += "\n" + when (pingResponse) {
                    is Success -> screen += "from:${pingResponse.origin} --- time:${pingResponse.time} --- icmp_seq:${pingResponse.icmpSequence} "
                    TimeOut -> "Ping request timeout"
                }
                viewAction.value = UpdateScreen(screen, time * 1000L)
            }
        }
    }


}