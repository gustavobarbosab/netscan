package com.network.scanner.speed.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network.scanner.common.BuildConfig
import com.network.scanner.common.SingleLiveEvent
import com.network.scanner.common.validations.IpValidator
import com.network.scanner.core.scanner.domain.NetScan
import com.network.scanner.core.scanner.domain.entities.NetworkSpeedResult
import com.network.scanner.core.scanner.domain.entities.PortScanResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.absoluteValue

class NetworkSpeedViewModel(private val netScan: NetScan) : ViewModel() {

    val screenState = SingleLiveEvent<NetworkSpeedScreenState>()

    fun checkSpeed() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            screenState.value = NetworkSpeedScreenState.DeviceNotSupported
            return
        }

        viewModelScope.launch {
            var speed: NetworkSpeedResult?
            withContext(Dispatchers.IO) {
                speed = netScan.checkNetworkSpeed()
            }
            if (speed == null) {
                screenState.value = NetworkSpeedScreenState.Failure
                return@launch
            }
            val download = (speed?.downstreamKbps ?: 0) / 1000
            val upload = (speed?.upstreamKbps ?: 0) / 1000
            screenState.value = NetworkSpeedScreenState.UpdateSpeed(
                download.toString(),
                upload.toString()
            )
        }

    }

    companion object {
        private const val MAX_PORT_NUMBER = 65535
    }
}