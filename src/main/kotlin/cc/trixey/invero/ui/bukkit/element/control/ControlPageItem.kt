package cc.trixey.invero.ui.bukkit.element.control

import cc.trixey.invero.ui.bukkit.element.item.SimpleItem
import cc.trixey.invero.ui.common.Panel
import cc.trixey.invero.ui.common.panel.PagedPanel

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.element.control.ControlPageItem
 *
 * @author Arasple
 * @since 2023/1/11 21:09
 */
class ControlPageItem(panel: Panel, private val target: PagedPanel, private val amount: Int = +1) : SimpleItem(panel) {

    init {
        onClick { _, _ -> target.nextPage(amount) }
    }

}