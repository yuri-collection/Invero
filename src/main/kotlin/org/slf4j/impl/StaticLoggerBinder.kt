package org.slf4j.impl

import cc.trixey.invero.common.logger.InveroLoggerFactory
import org.slf4j.ILoggerFactory
import org.slf4j.spi.LoggerFactoryBinder

/**
 * SLF4J根据特定路径查找此类
 * 这是SLF4J查找日志实现的标准机制
 */
class StaticLoggerBinder private constructor() : LoggerFactoryBinder {

    companion object {

        @JvmField
        val REQUESTED_API_VERSION = "1.7.36" 

        @JvmField
        val SINGLETON = StaticLoggerBinder()

        @JvmStatic
        fun getSingleton(): StaticLoggerBinder {
            return SINGLETON
        }
    }

    private val loggerFactoryClassStr = InveroLoggerFactory::class.java.name

    private val loggerFactory = InveroLoggerFactory()

    override fun getLoggerFactory(): ILoggerFactory {
        return loggerFactory
    }

    override fun getLoggerFactoryClassStr(): String {
        return loggerFactoryClassStr
    }
} 