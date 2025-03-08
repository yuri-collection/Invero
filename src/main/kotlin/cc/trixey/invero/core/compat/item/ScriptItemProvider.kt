package cc.trixey.invero.core.compat.item

import cc.trixey.invero.common.ItemSourceProvider
import cc.trixey.invero.core.Context
import cc.trixey.invero.core.compat.DefItemProvider
import cc.trixey.invero.core.util.KetherHandler
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Invero
 * cc.trixey.invero.core.compat.item.ScriptItemProvider
 *
 * @author Arasple
 * @since 2023/2/2 14:00
 */
@DefItemProvider(["kether"])
class ScriptItemProvider : ItemSourceProvider {

    override fun translateIdentifier(): Boolean {
        return false
    }

    override fun getItem(identifier: String, context: Any?): ItemStack? {
        return (context as? Context)?.let {
            val player = it.viewer.get<Player>()
            KetherHandler.invoke(identifier, player, it.variables).getNow(null) as? ItemStack
        }
    }

}