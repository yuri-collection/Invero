package cc.trixey.invero.core.compat.item

import cc.trixey.invero.common.ItemSourceProvider
import cc.trixey.invero.core.compat.DefItemProvider
import cc.trixey.invero.core.compat.PluginHook
import dev.lone.itemsadder.api.CustomStack
import org.bukkit.inventory.ItemStack

/**
 * Invero
 * cc.trixey.invero.core.compat.item.ItemsAdderItemProvider
 *
 * @author Arasple
 * @since 2023/1/29 15:50
 */
@DefItemProvider(["itemsadder", "ia"])
class ItemsAdderItemProvider : ItemSourceProvider, PluginHook() {

    override val pluginName = "ItemsAdder"

    override fun getItem(identifier: String, context: Any?): ItemStack? {
        return CustomStack.getInstance(identifier)?.itemStack
    }

}