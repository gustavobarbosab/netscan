package com.network.scanner.core.scanner.domain.tools

import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.scanner.domain.entities.NetworkSpeedResult

interface NetworkSpeed {
    @RequiresApi(Build.VERSION_CODES.M)
    fun checkSpeed(): NetworkSpeedResult
}