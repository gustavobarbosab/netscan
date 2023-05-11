package com.network.scanner.core.domain.tools

interface Worker<RETURN> {
    fun execute(): RETURN
}
