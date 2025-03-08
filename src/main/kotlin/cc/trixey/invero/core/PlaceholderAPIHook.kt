package cc.trixey.invero.core

import cc.trixey.invero.common.Invero
import org.bukkit.entity.Player
import taboolib.platform.compat.PlaceholderExpansion
import taboolib.platform.util.bukkitPlugin

/**
 * Invero
 * cc.trixey.invero.core.PlaceholderAPIHook
 *
 * @author Arasple
 * @since 2023/5/26 21:18
 */
object PlaceholderAPIHook : PlaceholderExpansion {

    override val identifier: String = bukkitPlugin.name

    override val autoReload: Boolean
        get() = true

    /**
     * invero_data_global_<key>
     * invero_data_player_<key>
     */
    override fun onPlaceholderRequest(player: Player?, args: String): String {
        if (player == null || !player.isOnline) return "<OFFLINE_PLAYER>"

        when {
            args.startsWith("data_global") -> {
                val key = "global@" + args.substring("data_global_".length)
                return Invero.API.getDataManager().getGlobalData()[key] ?: "<NULL: $key>"
            }

            args.startsWith("data_player") -> {
                val key = "player@" + args.substring("data_player_".length)
                return Invero.API.getDataManager().getPlayerData(player)[key] ?: "<NULL: $key>"
            }
        }

        return "<UNKNOWN_OPERATOR>"
    }
}