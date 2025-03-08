package cc.trixey.invero.core.compat.bungeecord

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.platform.util.bukkitPlugin
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream

/**
 * Invero
 * cc.trixey.invero.core.compat.bungeecord.Bungees
 *
 * @author Arasple
 * @since 2023/1/31 20:21
 */
object Bungees {

    init {
        if (!Bukkit.getMessenger().isOutgoingChannelRegistered(bukkitPlugin, "BungeeCord")) {
            Bukkit.getMessenger().registerOutgoingPluginChannel(bukkitPlugin, "BungeeCord")
        }
    }

    fun connect(player: Player, server: String) {
        sendBungeeData(player, "Connect", server)
    }

    fun sendBungeeData(player: Player, vararg args: String) {
        val byteArray = ByteArrayOutputStream()
        val out = DataOutputStream(byteArray)
        for (arg in args) out.writeUTF(arg)
        player.sendPluginMessage(bukkitPlugin, "BungeeCord", byteArray.toByteArray())
    }

}