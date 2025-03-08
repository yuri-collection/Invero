package cc.trixey.invero.ui.bukkit.api.dsl

import cc.trixey.invero.ui.bukkit.*
import cc.trixey.invero.ui.common.ContainerType
import cc.trixey.invero.ui.common.ContainerType.ANVIL
import taboolib.common.platform.function.warning

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.api.dsl.Window
 *
 * @author Arasple
 * @since 2023/1/5 13:29
 */
inline fun commonWindow(
    viewer: PlayerViewer,
    type: ContainerType,
    title: String = "Untitled",
    hidePlayerInventory: Boolean = true,
    overridePlayerInventory: Boolean = true,
    virtual: Boolean = true,
    block: BukkitWindow.() -> Unit = {}
): BukkitWindow {

    return if (type.isOrdinaryChest) {
        chestWindow(viewer, type.rows, title, hidePlayerInventory, overridePlayerInventory, virtual, block)
    } else when (type) {
        ANVIL -> WindowAnvil(title, viewer, hidePlayerInventory, overridePlayerInventory, virtual).also(block)
        else -> WindowDefault(type, title, viewer, hidePlayerInventory, overridePlayerInventory, virtual).also(block)
            .also {
                warning("You are using an unfully supported window type: $type")
            }
    }
}

inline fun chestWindow(
    viewer: PlayerViewer,
    rows: Int,
    title: String = "Untitled",
    hidePlayerInventory: Boolean = true,
    overridePlayerInventory: Boolean = true,
    virtual: Boolean = true,
    block: WindowChest.() -> Unit = {}
): WindowChest {
    return WindowChest(rows, title, viewer, hidePlayerInventory, overridePlayerInventory, virtual).also(block)
}