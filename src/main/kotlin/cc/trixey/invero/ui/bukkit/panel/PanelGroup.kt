package cc.trixey.invero.ui.bukkit.panel

import cc.trixey.invero.ui.bukkit.BukkitPanel
import cc.trixey.invero.ui.bukkit.PanelContainer
import cc.trixey.invero.ui.common.Element
import cc.trixey.invero.ui.common.Pos
import cc.trixey.invero.ui.common.Scale
import cc.trixey.invero.ui.common.event.ClickType
import cc.trixey.invero.ui.common.panel.PanelWeight
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.panel.PanelGroup
 *
 * @author Arasple
 * @since 2023/1/10 20:50
 */
class PanelGroup(
    parent: PanelContainer,
    weight: PanelWeight,
    scale: Scale,
    locate: Pos
) : BukkitPanel(parent, weight, scale, locate), PanelContainer {

    override val panels = arrayListOf<BukkitPanel>()

    override fun render() = panels.forEach { it.render() }

    override fun isElementValid(element: Element) = panels.any { it.isElementValid(element) }

    override fun handleClick(pos: Pos, clickType: ClickType, e: InventoryClickEvent?): Boolean {
        val clicked = pos - locate
        panels.forEach {
            if (clicked in it.area) {
                it.handleClick(pos - it.locate, clickType, e)
                return true
            }
        }
        return false
    }

    override fun handleDrag(pos: List<Pos>, e: InventoryDragEvent): Boolean {
        val affected = pos.map { it - locate }
        panels
            .find { panel -> affected.all { it in panel.area } }
            ?.let {
                return it.handleDrag(affected, e)
            }
        return false
    }

    override fun handleItemsMove(pos: Pos, e: InventoryClickEvent): Boolean {
        val clicked = pos - locate
        panels.forEach {
            if (clicked in it.area) {
                it.handleItemsMove(pos - it.locate, e)
                return true
            }
        }
        return false
    }

}