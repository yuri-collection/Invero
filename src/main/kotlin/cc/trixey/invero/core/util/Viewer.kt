package cc.trixey.invero.core.util

import cc.trixey.invero.core.Session
import cc.trixey.invero.ui.bukkit.PlayerViewer
import cc.trixey.invero.ui.common.Window
import org.bukkit.entity.Player

/**
 * Invero
 * cc.trixey.invero.core.util.Viewer
 *
 * @author Arasple
 * @since 2023/1/16 12:12
 */

inline val PlayerViewer.session: Session?
    get() = Session.getSession(this)

inline val Player.session: Session?
    get() = Session.getSession(PlayerViewer(this))

fun PlayerViewer.unregisterSession(callback: (Window) -> Unit = {}): Session? {
    return session?.also {
        Session.unregister(it)
        callback(it.window)
    }
}