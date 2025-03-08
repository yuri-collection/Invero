package cc.trixey.invero.ui.bukkit.api.dsl

import cc.trixey.invero.ui.bukkit.PlayerViewer
import org.bukkit.entity.Player

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.api.dsl.Viewer
 *
 * @author Arasple
 * @since 2023/1/20 15:55
 */
val String.asViewer: PlayerViewer
    get() = PlayerViewer(this)

val Player.viewer: PlayerViewer
    get() = PlayerViewer(this)