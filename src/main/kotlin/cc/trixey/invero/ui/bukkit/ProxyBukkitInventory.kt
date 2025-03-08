package cc.trixey.invero.ui.bukkit

import cc.trixey.invero.ui.common.ProxyInventory
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.ProxyBukkitInventory
 *
 * @author Arasple
 * @since 2023/1/20 13:15
 */
interface ProxyBukkitInventory : ProxyInventory {

    val viewer: Player?
        get() = window.viewer.get<Player>()

    val hidePlayerInventory: Boolean

    operator fun get(slot: Int): ItemStack?

    operator fun set(slot: Int, itemStack: ItemStack?)

    fun Int.outflowCorrect() = (this - containerSize).let {
        if (it > 26) it - 27 else it + 9
    }

    fun isVirtual(): Boolean

}