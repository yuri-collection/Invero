package cc.trixey.invero.ui.bukkit.panel

import cc.trixey.invero.ui.bukkit.BukkitPanel
import cc.trixey.invero.ui.bukkit.PanelContainer
import cc.trixey.invero.ui.common.Element
import cc.trixey.invero.ui.common.Panel
import cc.trixey.invero.ui.common.Pos
import cc.trixey.invero.ui.common.Scale
import cc.trixey.invero.ui.common.event.ClickType
import cc.trixey.invero.ui.common.panel.PagedPanel
import cc.trixey.invero.ui.common.panel.PanelWeight
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.panel.PagedNetesedPanel
 *
 * @author Arasple
 * @since 2023/1/9 16:04
 */
open class PagedNetesedPanel(
    parent: PanelContainer,
    weight: PanelWeight,
    scale: Scale,
    locate: Pos,
    defaultPage: Int = 0
) : BukkitPanel(parent, weight, scale, locate), PagedPanel, PanelContainer {

    override val panels = arrayListOf<BukkitPanel>()

    override val maxPageIndex: Int
        get() = panels.lastIndex

    override var pageIndex: Int = defaultPage
        set(value) {
            pageChangeCallback(this, field, value)
            if (value < 0) error("Page index can not be a negative number")
            else if (pageIndex > maxPageIndex) error("Page index is out of bounds $maxPageIndex")
            field = value
            render()
        }

    override var pageChangeCallback: PagedPanel.(fromPage: Int, toPage: Int) -> Unit = { _, _ -> }

    val currentPanel: BukkitPanel
        get() = panels[pageIndex]

    override fun render() = currentPanel.rerender()

    override fun isElementValid(element: Element) = currentPanel.isElementValid(element)

    override fun isPanelValid(panel: Panel) = currentPanel == panel

    override fun handleClick(pos: Pos, clickType: ClickType, e: InventoryClickEvent?): Boolean {
        return currentPanel.handleClick(pos, clickType, e)
    }

    override fun handleDrag(pos: List<Pos>, e: InventoryDragEvent): Boolean {
        return currentPanel.handleDrag(pos, e)
    }

    override fun handleItemsMove(pos: Pos, e: InventoryClickEvent): Boolean {
        return currentPanel.handleItemsMove(pos, e)
    }

}