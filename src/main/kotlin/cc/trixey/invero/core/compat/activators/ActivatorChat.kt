package cc.trixey.invero.core.compat.activators

import cc.trixey.invero.common.Invero
import cc.trixey.invero.common.MenuActivator
import cc.trixey.invero.core.compat.DefActivator
import cc.trixey.invero.ui.bukkit.util.proceed
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import org.bukkit.entity.Player
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import taboolib.common.platform.event.SubscribeEvent

/**
 * Invero
 * cc.trixey.invero.core.compat.activators.ActivatorChat
 *
 * @author Arasple
 * @since 2023/2/25 13:55
 */
@DefActivator(["chat", "chats"])
class ActivatorChat(private val chats: List<String> = emptyList()) : MenuActivator<ActivatorChat>() {

    companion object {

        @SubscribeEvent(ignoreCancelled = true)
        fun e(e: AsyncPlayerChatEvent) {
            val player = e.player
            val message = e.message
            Invero.API.getRegistry().callActivator(player, "CHAT", message).proceed {
                e.isCancelled = true
            }
        }

        @SubscribeEvent(ignoreCancelled = true)
        fun e(e: PlayerCommandPreprocessEvent) {
            val player = e.player
            val message = e.message

            Invero.API.getRegistry().callActivator(player, "CHAT", message).proceed {
                e.isCancelled = true
            }
        }

    }

    override fun call(player: Player, vararg params: Any): Boolean {
        val message = params[0] as String

        if (chats.any { message.equals(it, true) }) {
            activate(player, buildMap {
                put("activator_message", message)
            })
            return true
        }
        return false
    }

    override fun deserialize(element: JsonElement): ActivatorChat {
        return when (element) {
            is JsonPrimitive -> ActivatorChat(listOf(element.content))
            is JsonArray -> ActivatorChat(element.map { it.jsonPrimitive.content })
            else -> error("Unsupported type $element")
        }
    }

    override fun serialize(activator: ActivatorChat): JsonElement {
        val chats = activator.chats
        chats.singleOrNull()?.let {
            return JsonPrimitive(chats.first())
        }
        return JsonArray(chats.map { JsonPrimitive(it) })
    }

}