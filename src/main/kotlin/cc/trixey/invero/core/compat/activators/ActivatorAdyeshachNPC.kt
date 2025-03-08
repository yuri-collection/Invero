package cc.trixey.invero.core.compat.activators

import cc.trixey.invero.common.Invero
import cc.trixey.invero.common.MenuActivator
import cc.trixey.invero.core.compat.DefActivator
import ink.ptms.adyeshach.core.event.AdyeshachEntityInteractEvent
import kotlinx.serialization.json.*
import org.bukkit.entity.Player
import taboolib.common.platform.Ghost
import taboolib.common.platform.event.SubscribeEvent

/**
 * Invero
 * cc.trixey.invero.core.compat.activators.ActivatorAdyeshachNPC
 *
 * @author Arasple
 * @since 2023/2/25 17:13
 */
@DefActivator(["ady", "adyeshach"])
class ActivatorAdyeshachNPC(private val ids: List<String> = emptyList()) : MenuActivator<ActivatorAdyeshachNPC>() {

    companion object {

        @SubscribeEvent(ignoreCancelled = true)
        @Ghost
        fun e(e: AdyeshachEntityInteractEvent) {
            if (e.isMainHand) {
                val player = e.player
                val id = e.entity.id
                Invero.API.getRegistry().callActivator(player, "ADYESHACH", id)
            }
        }

    }

    override fun call(player: Player, vararg params: Any): Boolean {
        val id = params[0] as String

        if (ids.any { it.equals(id, true) }) {
            activate(player, buildMap {
                put("activator_ady", id)
            })
            return true
        }
        return false
    }

    override fun deserialize(element: JsonElement): ActivatorAdyeshachNPC {
        return when (element) {
            is JsonPrimitive -> ActivatorAdyeshachNPC(listOf(element.content))
            is JsonArray -> ActivatorAdyeshachNPC(element.map { it.jsonPrimitive.content })
            else -> error("Unsupported type $element")
        }
    }

    override fun serialize(activator: ActivatorAdyeshachNPC) = buildJsonArray {
        activator.ids.forEach { add(it) }
    }

}