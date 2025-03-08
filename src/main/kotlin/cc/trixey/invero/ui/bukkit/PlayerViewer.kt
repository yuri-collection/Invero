package cc.trixey.invero.ui.bukkit

import cc.trixey.invero.ui.common.Viewer
import org.bukkit.entity.Player
import taboolib.common.platform.function.getProxyPlayer

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.PlayerViewer
 *
 * @author Arasple
 * @since 2023/1/20 13:35
 */
class PlayerViewer(override val name: String) : Viewer {

    constructor(player: Player) : this(player.name)

    override fun isAvailable(): Boolean {
        return get<Player>()?.isOnline == true
    }

    override fun <T> get(): T? {
        return getProxyPlayer(name)?.cast()
    }
}