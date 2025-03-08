package cc.trixey.invero.common

import cc.trixey.invero.common.api.InveroAPI
import cc.trixey.invero.common.logger.LoggerBlocker
import org.slf4j.LoggerFactory
import taboolib.common.LifeCycle
import taboolib.common.event.InternalEventBus
import taboolib.common.platform.Awake
import taboolib.common.platform.PlatformFactory
import taboolib.common.platform.function.console
import taboolib.common.platform.function.getOpenContainers
import taboolib.common.platform.function.info
import taboolib.module.nms.MinecraftVersion
import taboolib.module.nms.PacketSendEvent
import taboolib.module.ui.isRawTitleInVanillaInventoryEnabled
import taboolib.module.ui.virtual.InventoryHandler
import taboolib.platform.util.bukkitPlugin

/**
 * Invero
 * cc.trixey.invero.common.Invero
 *
 * @author Arasple
 * @since 2023/2/1 17:13
 */
object Invero {

    val API: InveroAPI by lazy { PlatformFactory.getAPI() }
    private val logger = LoggerFactory.getLogger(Invero::class.java)

    /**
     * 在构造函数中拦截SLF4J日志
     */
    init {
        LoggerBlocker.init()
    }

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        LoggerBlocker.init()
        
        enableRawTitleInVanillaInventory()
        
        // 显示启动信息
        console().sendMessage("§3 ___                           ")
        console().sendMessage("§3|_ _|§b_ ____   §3_____ §b_ __ §3___  ")
        console().sendMessage("§3 | |§b| '_ \\ \\ §3/ / §b_ \\ '__§3/ _ \\ ")
        console().sendMessage("§3 | |§b| | | \\ §3V /  §b__/ | §3| (_) |")
        console().sendMessage("§3|___|§b_| |_|\\§3_/ \\§b___|_|  §3\\___/ ")
        console().sendMessage(" ")
        console().sendMessage("§8Invero §7v${bukkitPlugin.description.version} §8- §7MC 1.21.4")
        console().sendMessage("§7By Arasple, Maintained by Lythrilla")
        console().sendMessage("§7QQ群: §f489868834 §8| §7GitHub: §fhttps://github.com/ythrilla/Invero")
        console().sendMessage(" ")
    }

    /**
     * 允许在 Vanilla Inventory 中使用 Raw Title
     *
     * 为什么需要主动启用?
     * 1. 一旦注册 [PacketSendEvent] 事件，就需要注入玩家的 Channel 来启用数据包系统。
     * 2. 对于没有这类需求的用户来说，安装 module-ui 就意味着被迫启用数据包系统，造成不必要的性能损耗。
     *
     * 虚拟菜单不需要开启该选项
     */
    private fun enableRawTitleInVanillaInventory() {
        // 防止重复注册
        if (isRawTitleInVanillaInventoryEnabled) {
            return
        }
        // 监听数据包
        InternalEventBus.listen<PacketSendEvent> { e ->
            if (e.packet.name == "PacketPlayOutOpenWindow" || e.packet.name == "ClientboundOpenScreenPacket") {
                // 1.20.5 -> d, 不再是 c
                val field = when {
                    MinecraftVersion.versionId >= 12005 -> "d"
                    MinecraftVersion.isUniversal -> "title"
                    else -> "c"
                }
                val plain = InventoryHandler.instance.craftChatMessageToPlain(e.packet.read(field)!!)
                if (plain.startsWith('{') && plain.endsWith('}')) {
                    e.packet.write(field, InventoryHandler.instance.parseToCraftChatMessage(plain))
                }
            }
        }
        // 告知所有 TabooLib 插件，该监听器已被注册
        getOpenContainers().forEach { it.call("LISTEN_RAW_TITLE_IN_VANILLA_INVENTORY", emptyArray()) }
    }
}