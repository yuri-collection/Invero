package cc.trixey.invero.common

import org.bukkit.inventory.ItemStack

/**
 * Invero
 * cc.trixey.invero.common.ItemSourceProvider
 *
 * @author Arasple
 * @since 2023/2/1 16:42
 */
interface ItemSourceProvider {

    fun translateIdentifier(): Boolean {
        return true
    }

    fun getItem(identifier: String, context: Any?): ItemStack?

}