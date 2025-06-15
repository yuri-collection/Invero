package cc.trixey.invero.core.command.sub

import cc.trixey.invero.common.Invero
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.adaptCommandSender
import taboolib.module.lang.sendLang

/**
 * 更新检查命令
 */
@CommandHeader(name = "update", permission = "invero.command.update", description = "Check for plugin updates")
object CommandUpdate {

    val check = subCommand {
        execute<CommandSender> { sender, _, _ ->
            // 发送检查更新消息
            adaptCommandSender(sender).sendLang("update-checking")
            
            // 如果是玩家，发送给玩家，否则发送给控制台
            if (sender is Player) {
                Invero.updateChecker.sendUpdateInfo(sender)
            } else {
                Invero.updateChecker.sendUpdateInfoToConsole()
            }
        }
    }
}
