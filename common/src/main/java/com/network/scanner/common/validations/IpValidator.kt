package com.network.scanner.common.validations

object IpValidator {
    private val IPV4 = "\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}".toRegex()

    fun isIPv4(address: String?) = IPV4.matches(address.orEmpty())
}