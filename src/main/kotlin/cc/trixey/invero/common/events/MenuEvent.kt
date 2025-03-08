package cc.trixey.invero.common.events

import cc.trixey.invero.common.Menu
import cc.trixey.invero.ui.bukkit.BukkitWindow
import org.bukkit.entity.Player

/**
 * Invero
 * cc.trixey.invero.common.events.MenuEvent
 *
 * @author Arasple
 * @since 2023/2/27 13:08
 */
interface MenuEvent {

    val viewer: Player

    val menu: Menu

    val window: BukkitWindow

}