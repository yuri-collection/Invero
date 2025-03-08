package cc.trixey.invero.ui.bukkit.element.control

import cc.trixey.invero.ui.bukkit.element.item.SimpleItem
import cc.trixey.invero.ui.common.Panel
import cc.trixey.invero.ui.common.panel.ScrollPanel

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.element.control.ControlPageItem
 *
 * @author Arasple
 * @since 2023/1/11 21:19
 */
class ControlScrollItem(panel: Panel, private val target: ScrollPanel, private val amount: Int = +1) :
    SimpleItem(panel) {

    init {
        onClick { _, _ -> target.scroll(amount) }
    }

}