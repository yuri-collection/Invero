package cc.trixey.invero.core.compat.item

import cc.trixey.invero.common.ItemSourceProvider
import cc.trixey.invero.core.compat.DefItemProvider
import cc.trixey.invero.core.compat.PluginHook
import io.th0rgal.oraxen.api.OraxenItems
import org.bukkit.inventory.ItemStack

/**
 * Invero
 * cc.trixey.invero.core.compat.item.OraxenItemProvider
 *
 * @author Arasple
 * @since 2023/1/29 15:41
 */
@DefItemProvider(["oraxen"])
class OraxenItemProvider : ItemSourceProvider, PluginHook() {

    override val pluginName = "Oraxen"

    override fun getItem(identifier: String, context: Any?): ItemStack {
        return OraxenItems.getItemById(identifier).build()
    }

}