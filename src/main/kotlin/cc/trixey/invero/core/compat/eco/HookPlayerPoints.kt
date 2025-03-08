package cc.trixey.invero.core.compat.eco

import cc.trixey.invero.core.compat.PluginHook
import org.black_ixx.playerpoints.PlayerPoints
import org.bukkit.entity.Player

/**
 * Invero
 * cc.trixey.invero.core.compat.eco.HookPlayerPoints
 *
 * @author Arasple
 * @since 2023/1/31 20:39
 */
object HookPlayerPoints : PluginHook() {

    override val pluginName = "PlayerPoints"

    private val playerPointsAPI = PlayerPoints.getInstance().api

    fun look(player: Player): Int? {
        return playerPointsAPI?.look(player.uniqueId)
    }

    fun add(player: Player, amount: Int) {
        playerPointsAPI?.give(player.uniqueId, amount)
    }

    fun take(player: Player, amount: Int) {
        playerPointsAPI?.take(player.uniqueId, amount.coerceAtLeast(0))
    }

    fun set(player: Player, amount: Int) {
        playerPointsAPI?.set(player.uniqueId, amount)
    }

}