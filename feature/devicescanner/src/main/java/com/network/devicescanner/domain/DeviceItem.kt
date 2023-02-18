package com.network.devicescanner.domain

import com.network.devicescanner.R

data class DeviceItem(
    val address: String,
    val port23Open: Boolean?,
    val port2323Open: Boolean?,
    val port48101Open: Boolean?,
) {

    fun iconToShow() = if (isDeviceSafe()) R.drawable.ic_safe else R.drawable.ic_alert

    fun isDeviceSafe() = port23Open == false && port2323Open == false && port48101Open == false

    fun port23Text() = if (port23Open == true) R.string.device_scanner_port_23_opened
    else R.string.device_scanner_port_23_closed

    fun port2323Text() = if (port2323Open == true) R.string.device_scanner_port_2323_opened
    else R.string.device_scanner_port_2323_closed

    fun port48101OText() = if (port48101Open == true) R.string.device_scanner_port_48101_opened
    else R.string.device_scanner_port_48101_closed
}