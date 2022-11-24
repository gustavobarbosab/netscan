package com.network.scanner.core.scanner.tools.networkdevices

import com.network.scanner.core.scanner.facade.NetScanFacade

class NetworkDevicesImpl(
    private val netScanFacade: NetScanFacade
) : NetworkDevices {

    override fun findDevices(): List<NetworkDevices.DeviceAddress> {
        val deviceList = mutableListOf<NetworkDevices.DeviceAddress>()
//        val myIpAddress = netScanFacade.getMyIpAddress()
//        val filteredIpAddress = myIpAddress.substringBeforeLast(DOT).plus(DOT)
//
//        repeat(TOTAL_IPV4_ADDRESS_IN_SUBNET) { lastDigits ->
//            val possibleDeviceAddress = filteredIpAddress + lastDigits
//            runCatching {
//                val pingResponse = pingDevice.ping(possibleDeviceAddress)
//
//                if (pingResponse is TimeOut)
//                    return@repeat
//
//                deviceList.add(
//                    NetworkDevices.DeviceAddress(
//                        ip = possibleDeviceAddress,
//                        mac = ""
//                    )
//                )
//            }
//        }

        return deviceList
    }

    companion object {
        private const val DOT = '.'
        private const val TOTAL_IPV4_ADDRESS_IN_SUBNET = 255
    }
}