package cc.trixey.invero.ui.bukkit.util

import cc.trixey.invero.ui.common.event.ClickType
import org.bukkit.event.inventory.InventoryClickEvent

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.util.Bukkit
 *
 * @author Arasple
 * @since 2023/2/11 16:41
 */
val InventoryClickEvent.clickType: ClickType
    get() = ClickType.findBukkit(click.name, hotbarButton)