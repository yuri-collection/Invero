package org.slf4j.impl

import org.slf4j.IMarkerFactory
import org.slf4j.helpers.BasicMarkerFactory
import org.slf4j.spi.MarkerFactoryBinder

/**
 * SLF4J标记绑定
 * 提供标记创建功能
 */
class StaticMarkerBinder private constructor() : MarkerFactoryBinder {

    companion object {

        @JvmField
        val SINGLETON = StaticMarkerBinder()

        @JvmStatic
        fun getSingleton(): StaticMarkerBinder {
            return SINGLETON
        }
    }

    private val markerFactory = BasicMarkerFactory()

    override fun getMarkerFactory(): IMarkerFactory {
        return markerFactory
    }

    override fun getMarkerFactoryClassStr(): String {
        return BasicMarkerFactory::class.java.name
    }
}