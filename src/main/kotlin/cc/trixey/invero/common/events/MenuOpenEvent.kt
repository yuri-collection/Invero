package cc.trixey.invero.common.events

import cc.trixey.invero.common.Menu
import cc.trixey.invero.ui.bukkit.BukkitWindow
import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * Invero
 * cc.trixey.invero.common.events.MenuOpenEvent
 *
 * @author Arasple
 * @since 2023/2/27 13:07
 */
class MenuOpenEvent(
    override val viewer: Player,
    override val menu: Menu,
    override val window: BukkitWindow,
    val openContext: Map<String, Any>
) : MenuEvent, BukkitProxyEvent()