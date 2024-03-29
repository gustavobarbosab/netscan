package com.network.scanner.pingdevice.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network.scanner.common.SingleLiveEvent
import com.network.scanner.core.domain.NetScan
import com.network.scanner.core.domain.entities.PingResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PingDeviceViewModel(private val netScan: NetScan) : ViewModel() {

    val screenState = SingleLiveEvent<PingDeviceScreenState>()
    val localAddress = SingleLiveEvent<String>()

    fun findMyAddress() {
        viewModelScope.launch {
            var address: String
            withContext(Dispatchers.IO) {
                address = netScan.getMyAddress()
            }
            if (address.isBlank()) {
                return@launch
            }
            localAddress.value = address
        }

    }

    fun pingDevice(ipAddress: String?) {
        val isValidAddress = REGEX.matches(ipAddress.orEmpty())
        if (!isValidAddress) {
            screenState.value = PingDeviceScreenState.InvalidAddress
            return
        }
        viewModelScope.launch {
            var result: PingResult? = null
            screenState.value = PingDeviceScreenState.Searching
            withContext(Dispatchers.IO) {
                runCatching {
                    result = netScan.ping(ipAddress!!)
                }
            }

            if (result != null) onPingSuccess()
            else onPingFailure()
            screenState.value = PingDeviceScreenState.ScreenStarted
        }
    }

    private fun onPingSuccess() {
        screenState.value = PingDeviceScreenState.DeviceFound
    }

    private fun onPingFailure() {
        screenState.value = PingDeviceScreenState.DeviceNotFound
    }

    companion object {
        private val REGEX = "\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}".toRegex()
    }
}