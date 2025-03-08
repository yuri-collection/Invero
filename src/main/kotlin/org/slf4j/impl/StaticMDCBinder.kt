package org.slf4j.impl

import org.slf4j.helpers.NOPMDCAdapter
import org.slf4j.spi.MDCAdapter

/**
 * SLF4J MDC适配器绑定
 * 提供MDC上下文功能
 */
class StaticMDCBinder private constructor() {

    companion object {

        @JvmField
        val SINGLETON = StaticMDCBinder()

        @JvmStatic
        fun getSingleton(): StaticMDCBinder {
            return SINGLETON
        }
    }

    private val mdcAdapter = NOPMDCAdapter()

    fun getMDCA(): MDCAdapter {
        return mdcAdapter
    }

    fun getMDCAdapterClassStr(): String {
        return NOPMDCAdapter::class.java.name
    }
}