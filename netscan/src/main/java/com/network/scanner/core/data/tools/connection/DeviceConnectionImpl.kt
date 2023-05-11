package com.network.scanner.core.data.tools.connection

import com.network.scanner.core.data.facade.NetScanFacade
import com.network.scanner.core.data.facade.NetworkType
import com.network.scanner.core.domain.tools.DeviceConnection

class DeviceConnectionImpl(
    private val facade: NetScanFacade
) : DeviceConnection {

    override fun hasWifiConnection(): Boolean = facade.hasConnection(NetworkType.Wifi)

    override fun hasCellularConnection() = facade.hasConnection(NetworkType.Mobile)

    override fun hasEthernetConnection() = facade.hasConnection(NetworkType.Ethernet)

    override fun hasInternetConnection() = hasCellularConnection()
            || hasWifiConnection()
            || hasEthernetConnection()
}