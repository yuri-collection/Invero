package cc.trixey.invero.ui.bukkit.panel

import cc.trixey.invero.ui.bukkit.BukkitPanel
import cc.trixey.invero.ui.bukkit.PanelContainer
import cc.trixey.invero.ui.common.Element
import cc.trixey.invero.ui.common.Pos
import cc.trixey.invero.ui.common.Scale
import cc.trixey.invero.ui.common.event.ClickType
import cc.trixey.invero.ui.common.panel.FreeformPanel
import cc.trixey.invero.ui.common.panel.PanelWeight
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.panel.FreeformNetesedPanel
 *
 * @author Arasple
 * @since 2023/1/10 21:46
 */
class FreeformNetesedPanel(
    parent: PanelContainer,
    weight: PanelWeight,
    scale: Scale,
    locate: Pos
) : BukkitPanel(parent, weight, scale, locate), PanelContainer, FreeformPanel {

    override val panels = arrayListOf<BukkitPanel>()

    override var viewport: Pos = Pos.NIL
        set(value) {
            field = value
            rerender()
        }

    override fun render() {
        panels.forEach { it.render() }
    }

    override fun isElementValid(element: Element): Boolean {
        return panels.any { it.isElementValid(element) }
    }

    override fun handleClick(pos: Pos, clickType: ClickType, e: InventoryClickEvent?) = panels.any {
        val clicked = pos + viewport
        if (clicked in it.area) {
            it.handleClick(clicked - it.locate, clickType, e)
            return@any true
        }
        return@any false
    }

    override fun handleDrag(pos: List<Pos>, e: InventoryDragEvent): Boolean {
        val affected = pos.map { it + viewport }

        panels
            .find { panel -> affected.all { it in panel.area } }
            ?.let { return it.handleDrag(affected, e) }

        return false
    }

    override fun handleItemsMove(pos: Pos, e: InventoryClickEvent) = panels.any {
        val clicked = pos + viewport
        if (clicked in it.area) {
            it.handleItemsMove(clicked - it.locate, e)
            return@any true
        }
        return@any false
    }

}