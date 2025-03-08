package cc.trixey.invero.ui.bukkit.api

import cc.trixey.invero.ui.bukkit.BukkitWindow
import cc.trixey.invero.ui.bukkit.PlayerViewer
import java.util.concurrent.ConcurrentHashMap

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.api.WindowManager
 *
 * @author Arasple
 * @since 2023/1/20 13:08
 */
val registeredWindows = ConcurrentHashMap<String, BukkitWindow>()

fun findWindow(viewer: String): BukkitWindow? {
    return registeredWindows[viewer]
}

fun PlayerViewer.notViewingWindow(): Boolean {
    return registeredWindows[name] == null
}

fun BukkitWindow.registerWindow() {
    registeredWindows[viewer.name] = this
}

fun BukkitWindow.unregisterWindow() {
    registeredWindows.remove(viewer.name, this)
}

fun BukkitWindow.isRegistered(): Boolean {
    return registeredWindows.containsValue(this)
}