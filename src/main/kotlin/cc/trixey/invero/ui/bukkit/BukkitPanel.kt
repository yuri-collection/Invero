package cc.trixey.invero.ui.bukkit

import cc.trixey.invero.ui.common.Panel
import cc.trixey.invero.ui.common.Pos
import cc.trixey.invero.ui.common.Scale
import cc.trixey.invero.ui.common.event.ClickType
import cc.trixey.invero.ui.common.panel.PanelWeight
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.BukkitPanel
 *
 * @author Arasple
 * @since 2023/1/20 14:15
 */
abstract class BukkitPanel(
    override val parent: PanelContainer,
    override val weight: PanelWeight,
    scale: Scale,
    override val locate: Pos
) : Panel {

    var skipRender = false

    override val scale: Scale by lazy { scale.coerceIn(parent.scale) }

    override val area by lazy { scale.getArea(locate) }

    override val window by lazy { parent.getTopWindow() }

    // return Boolean ->> should continue to handle elemental click or not
    private val clickCallback = CopyOnWriteArrayList<(Pos, ClickType, InventoryClickEvent?) -> Boolean>()

    fun onClick(handler: (Pos, ClickType, InventoryClickEvent?) -> Boolean): BukkitPanel {
        clickCallback += handler
        return this
    }

    fun runClickCallbacks(pos: Pos, clickType: ClickType, e: InventoryClickEvent?): Boolean {
        return clickCallback.isEmpty() || clickCallback.all { it(pos, clickType, e) }
    }

    open fun handleClick(pos: Pos, clickType: ClickType, e: InventoryClickEvent?): Boolean {
        return true
    }

    open fun handleDrag(pos: List<Pos>, e: InventoryDragEvent): Boolean {
        return true
    }

    open fun handleItemsMove(pos: Pos, e: InventoryClickEvent): Boolean {
        return true
    }

}