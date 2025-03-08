package cc.trixey.invero.common

import cc.trixey.invero.ui.bukkit.PlayerViewer
import cc.trixey.invero.ui.bukkit.api.dsl.viewer
import org.bukkit.entity.Player

/**
 * Invero
 * cc.trixey.invero.common.Menu
 *
 * @author Arasple
 * @since 2023/2/1 16:50
 */
interface Menu {

    var id: String?

    fun open(player: Player, vars: Map<String, Any> = emptyMap()) = open(player.viewer, vars)

    fun close(player: Player, closeWindow: Boolean = true, closeInventory: Boolean = true) =
        close(player.viewer, closeWindow, closeInventory)

    fun open(viewer: PlayerViewer, vars: Map<String, Any> = emptyMap())

    fun close(viewer: PlayerViewer, closeWindow: Boolean = true, closeInventory: Boolean = true)

    fun isVirtual(): Boolean

    fun register()

    fun unregister()

}