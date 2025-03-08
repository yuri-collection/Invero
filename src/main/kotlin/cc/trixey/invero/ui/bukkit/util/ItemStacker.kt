package cc.trixey.invero.ui.bukkit.util

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.util.ItemStacker
 *
 * @author Arasple
 * @since 2023/1/12 13:57
 */
fun ItemStack.reachedMaxStackSize(): Boolean {
    return amount >= maxStackSize
}

fun Player.giveItem(itemStack: ItemStack) = inventory.addItem(itemStack).values.none { e ->
    world.dropItem(location, e)
    true
}
