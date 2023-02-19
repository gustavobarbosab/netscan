package com.network.scanner.portscan.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network.scanner.common.SingleLiveEvent
import com.network.scanner.common.validations.IpValidator
import com.network.scanner.core.scanner.domain.NetScan
import com.network.scanner.core.scanner.domain.entities.PortScanResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.absoluteValue

class PortScanViewModel(private val netScan: NetScan) : ViewModel() {

    val screenState = SingleLiveEvent<PortScanScreenState>()
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

    fun portScan(ipAddress: String?, port: String?) {
        val isValidAddress = IpValidator.isIPv4(ipAddress)
        if (!isValidAddress) {
            screenState.value = PortScanScreenState.InvalidAddress
            return
        }

        val portInteger = port?.toIntOrNull()?.absoluteValue

        if (portInteger == null || portInteger > MAX_PORT_NUMBER) {
            screenState.value = PortScanScreenState.InvalidPort
            return
        }
        viewModelScope.launch {
            var result: PortScanResult? = null
            screenState.value = PortScanScreenState.Searching
            withContext(Dispatchers.IO) {
                runCatching {
                    result = netScan.portScan(ipAddress!!, port.toInt(), 5000)
                }.onFailure {
                    Log.e("teste", "oi")
                }
            }

            if (result != null) onPortScanSuccess()
            else onPortScanFailure()
            screenState.value = PortScanScreenState.ScreenStarted
        }
    }

    private fun onPortScanSuccess() {
        screenState.value = PortScanScreenState.PortOpened
    }

    private fun onPortScanFailure() {
        screenState.value = PortScanScreenState.DeviceNotFound
    }

    companion object {
        private const val MAX_PORT_NUMBER = 65535
    }
}