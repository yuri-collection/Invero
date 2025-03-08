package cc.trixey.invero.ui.bukkit

import cc.trixey.invero.ui.common.ContainerType

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.WindowChest
 *
 * @author Arasple
 * @since 2023/1/20 13:13
 */
class WindowChest(
    rows: Int = 6,
    title: String,
    viewer: PlayerViewer,
    hidePlayerInventory: Boolean,
    overridePlayerInventory: Boolean,
    virtual: Boolean = true
) : WindowDefault(ContainerType.ofRows(rows), title, viewer, hidePlayerInventory, overridePlayerInventory, virtual)