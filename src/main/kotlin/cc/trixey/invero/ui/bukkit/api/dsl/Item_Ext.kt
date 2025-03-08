package cc.trixey.invero.ui.bukkit.api.dsl

import cc.trixey.invero.ui.bukkit.element.control.ControlPageItem
import cc.trixey.invero.ui.bukkit.element.control.ControlScrollItem
import cc.trixey.invero.ui.common.panel.ElementalPanel
import cc.trixey.invero.ui.common.panel.PagedPanel
import cc.trixey.invero.ui.common.panel.ScrollPanel
import org.bukkit.Material

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.api.dsl.Item_Ext
 *
 * @author Arasple
 * @since 2023/1/11 21:12
 */
inline fun ElementalPanel.pageController(
    target: PagedPanel,
    amount: Int = +1,
    slot: Int,
    material: Material,
    block: ControlPageItem.() -> Unit = {}
) {
    ControlPageItem(this, target, amount).apply {
        build(material)
        block(this)

        set(scale.convertToPosition(slot))
    }
}

inline fun ElementalPanel.scrollController(
    target: ScrollPanel,
    amount: Int = +1,
    slot: Int,
    material: Material,
    block: ControlScrollItem.() -> Unit = {}
) {
    ControlScrollItem(this, target, amount).apply {
        build(material)
        block(this)

        set(scale.convertToPosition(slot))
    }
}
