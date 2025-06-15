package cc.trixey.invero.core.listeners

import cc.trixey.invero.common.Invero
import cc.trixey.invero.common.api.InveroSettings
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit

/**
 * 玩家加入事件监听器
 */
object PlayerJoinListener {

    /**
     * 处理玩家加入事件
     */
    @SubscribeEvent
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        
        // 如果玩家是OP且配置中启用了更新检查，发送更新检查信息
        if (player.isOp && InveroSettings.updateCheckerEnabled) {
            // 在玩家加入后2秒发送更新信息，避免消息过多
            submit(delay = 40L) {  // 40 ticks = 2 seconds
                if (player.isOnline) {
                    sendUpdateInfo(player)
                }
            }
        }
    }
    
    /**
     * 向玩家发送更新信息
     */
    private fun sendUpdateInfo(player: Player) {
        Invero.updateChecker.sendUpdateInfo(player)
    }
}
