package cc.trixey.invero.core.command

import cc.trixey.invero.common.Invero
import cc.trixey.invero.core.util.KetherHandler
import cc.trixey.invero.core.util.session
import cc.trixey.invero.ui.bukkit.InventoryPacket
import cc.trixey.invero.ui.bukkit.InventoryVanilla
import cc.trixey.invero.ui.bukkit.PanelContainer
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.adaptCommandSender
import taboolib.common.platform.function.info
import taboolib.module.chat.component
import taboolib.platform.util.bukkitPlugin
import taboolib.platform.util.isAir
import taboolib.platform.util.onlinePlayers

/**
 * Invero
 * cc.trixey.invero.core.command.CommandDev
 *
 * @author Arasple
 * @since 2023/1/15 15:44
 */
@CommandHeader(name = "idev", permission = "invero.dev.access")
object CommandDev {

    @CommandBody
    val main = mainCommand { createHelper() }

    @CommandBody
    val runKether = subCommand {
        execute<CommandSender> { sender, _, argument ->
            val player = if (sender is Player) sender else onlinePlayers.random()
            val script = argument.removePrefix("runKether ")

            KetherHandler.invoke(script, player, mapOf()).thenApply {
                sender.sendMessage("§7Result: $it")
            }
        }
    }

    @CommandBody
    val testComponent = subCommand {
        execute<CommandSender> { sender, _, argument ->
            val message = argument.split(" ", limit = 2)[1]

            message
                .component()
                .build { colored() }
                .also {
                    it.toLegacyText()
                    info(
                        """
                            Component:: ${it.toLegacyText().replace('§', '&')}
                        """.trimIndent()
                    )
                }
                .sendTo(adaptCommandSender(sender))
        }
    }

    @CommandBody
    val printSerailizedMenu = subCommand {
        execute<CommandSender> { _, _, argument ->
            val menuId = argument.split(" ").getOrNull(1) ?: return@execute
            val menuManager = Invero.API.getMenuManager()

            menuManager.getMenu(menuId)?.let { info(menuManager.serializeToJson(it)) }
        }
    }

    @CommandBody
    val printTasks = subCommand {
        execute<CommandSender> { _, _, _ ->
            val activeWorkers = Bukkit.getScheduler().activeWorkers.count { it.owner == bukkitPlugin }
            val pendingTasks = Bukkit.getScheduler().pendingTasks.count { it.owner == bukkitPlugin }

            info(
                """
                    Tasks: (activeWorkers=$activeWorkers, pendingTasks=$pendingTasks)
                """.trimIndent()
            )
        }
    }

    @CommandBody
    val printVariables = subCommand {
        execute<CommandSender> { _, _, argument ->
            val globaldata = Invero.API.getDataManager().getGlobalData().source
            info(
                """
                    
                    [I][Print] ------------------------------ [Variables]
                    GlobalData: (${globaldata.keys.size})
                """.trimIndent()
            )
            globaldata.forEach { (key, value) ->
                info("- [${key.removePrefix("global@")}] : $value")
            }
            onlinePlayers.forEach {
                info("[I][PLAYER: ${it.name}] ------------------------------ [Player_Variables]")
                Invero.API.getDataManager().getPlayerData(it).source.forEach { (key, value) ->
                    info("- [${key.removePrefix("player@")}] : $value")
                }
            }
        }
    }

    @CommandBody
    val printSession = subCommand {
        execute<CommandSender> { _, _, argument ->
            val session = onlinePlayers.first().session ?: run {
                info("No session valid")
                return@execute
            }

            info(
                """
                    
                    [I][Print] ------------------------------ [SESSION]
                    Date: ${session.createdTime}
                    
                    Viewer: ${session.viewer.name}
                    Window: ${session.window.type.name}
                    CommandMenu: ${session.menu.id}
                    Variables: ${session.getVariables()}
                    TaskMgr: ${session.taskGroup}
                    --------------------------------------------------
                    
                """.trimIndent()
            )
        }
    }

    @CommandBody
    val printWindow = subCommand {
        execute<CommandSender> { _, _, argument ->
            val session = onlinePlayers.first().session ?: run {
                info("No session valid")
                return@execute
            }
            val player = session.viewer.get<Player>() ?: return@execute
            val window = session.window
            val inventory = window.inventory

            info(
                """
                    
                    [I][Print] ------------------------------ [WINDOW]
                    Bukkit Type: ${player.openInventory.topInventory.type} (${player.openInventory.topInventory.javaClass.simpleName})
                    Object: $window (${window.type.name})
                    Virtualized: ${window.inventory.isVirtual()}
                    Hosted Panels: ${window.panels.size}
                    -->
                """.trimIndent()
            )

            if (!window.inventory.isVirtual()) {
                val container = (window.inventory as InventoryVanilla).container
                info("Container: ${container.type} // ${container.size}")
            }

            fun dumpPanels(indent: String = " ", container: PanelContainer) {
                container.panels.forEachIndexed { index, panel ->
                    info(indent + "Panel#$index (${panel.scale} at ${panel.locate}) [${panel.javaClass.simpleName}]")
                    if (panel is PanelContainer) {
                        info("$indent  > __SUB PANELS__ (${panel.panels.size})")
                        dumpPanels("$indent  ", panel)
                    }
                }
            }

            dumpPanels(container = window)

            if (inventory is InventoryPacket) {
                inventory.windowItems
            } else {
                (inventory as InventoryVanilla).container.contents
            }.filterNot { it.isAir }.joinToString(",") { it!!.type.name }.let {
                info("Storage: [ $it ]")
            }
        }
    }
}