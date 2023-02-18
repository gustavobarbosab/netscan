package com.network.scanner.pingdevice.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network.scanner.common.SingleLiveEvent
import com.network.scanner.core.scanner.domain.NetScan
import com.network.scanner.core.scanner.domain.entities.NetScanObservableUnbind
import com.network.scanner.core.scanner.domain.entities.PingResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PingDeviceViewModel(private val netScan: NetScan) : ViewModel() {

    val screenState = SingleLiveEvent<PingDeviceScreenState>()
    private val netScanObservableUnbind = NetScanObservableUnbind()

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