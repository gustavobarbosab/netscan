package com.network.scanner.core.scanner.tools.ping

import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
interface PingDevice {
    fun ping(
        ipDestiny: String,
        timeToLive: Int = DEFAULT_TIME_TO_LIVE,
        timeout: Int = DEFAULT_TIMEOUT
    ): PingResponse

    sealed class PingResponse {
        data class Success(
            val origin: String,
            val ttl: Int,
            val time: Long
        ) : PingResponse()

        object TimeOut : PingResponse()
    }

    companion object {
        private const val DEFAULT_TIME_TO_LIVE = 200
        private const val DEFAULT_TIMEOUT = 50
    }
}