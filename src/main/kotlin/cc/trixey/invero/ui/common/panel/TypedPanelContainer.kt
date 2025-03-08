@file:Suppress("UNCHECKED_CAST")

package cc.trixey.invero.ui.common.panel

import cc.trixey.invero.ui.common.Gridable
import cc.trixey.invero.ui.common.Panel
import cc.trixey.invero.ui.common.Window

/**
 * Invero
 * cc.trixey.invero.ui.common.panel.TypedPanelContainer
 *
 * @author Arasple
 * @since 2022/12/22 14:50
 */
interface TypedPanelContainer<T : Panel> : Gridable {

    val panels: ArrayList<T>

    operator fun plusAssign(panel: T) {
        panels += panel
    }

    operator fun minusAssign(panel: T) {
        panels -= panel
    }

    operator fun contains(panel: Panel): Boolean {
        return panel in panels
    }

    fun <T : Gridable> cast(): T {
        return this as T
    }

    fun getPanelsRecursively(): List<Panel> {
        val result = mutableListOf<Panel>()

        panels.forEach {
            result += it
            if (it is TypedPanelContainer<*>) result += it.getPanelsRecursively()
        }

        return result
    }

    fun getTopWindow(): Window {
        return if (this is Window) this
        else (this as Panel).parent.getTopWindow()
    }

    fun isWindow(): Boolean {
        return this is Window
    }

    fun isPanel(): Boolean {
        return this is Panel
    }

    fun isElementalPanel(): Boolean {
        return this is ElementalPanel
    }

    fun isFreeform(): Boolean {
        return this is FreeformPanel
    }

    fun isPanelValid(panel: Panel): Boolean {
        return true
    }

}