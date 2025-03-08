package cc.trixey.invero.ui.bukkit.api.dsl

import cc.trixey.invero.ui.bukkit.PanelContainer
import cc.trixey.invero.ui.bukkit.panel.FreeformStandardPanel
import cc.trixey.invero.ui.common.Panel
import cc.trixey.invero.ui.common.panel.ElementalPanel
import cc.trixey.invero.ui.common.panel.FreeformPanel
import cc.trixey.invero.ui.common.panel.PagedPanel
import cc.trixey.invero.ui.common.panel.ScrollPanel

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.api.dsl.Tool
 *
 * @author Arasple
 * @since 2023/1/5 13:29
 */
fun PanelContainer.firstAvailablePositionForPanel(): Pair<Int, Int> {
    // pagedNetesedPanel
    if (this is PagedPanel) return 0 to 0
    val available = scale.getArea() - panels.flatMap { it.area }.toSet()

    if (available.isEmpty()) error("No available position for panel(${this.javaClass.simpleName})")
    return available.minBy { it.y }.value
}

fun PanelContainer.inheritParentScale(): Pair<Int, Int> {
    return scale.pair
}

fun ElementalPanel.firstAvailablePositionForElement(): Pair<Int, Int> {
    return getUnoccupiedPositions().minByOrNull { it.y * 9 + it.x }!!.value
}

inline fun <reified T : Panel> PanelContainer.findSubPanel(): T? {
    return panels.filterIsInstance<T>().firstOrNull()
}

fun PanelContainer.firstFreeform(): FreeformPanel = findSubPanel()!!

fun PanelContainer.firstFreeformStandard(): FreeformStandardPanel = findSubPanel()!!

fun PanelContainer.firstPagedPanel(): PagedPanel = findSubPanel()!!

fun PanelContainer.firstScrollPanel(): ScrollPanel = findSubPanel()!!