package cc.trixey.invero.core

import cc.trixey.invero.core.icon.IconElement
import cc.trixey.invero.common.message.translateFormattedMessage
import cc.trixey.invero.ui.bukkit.PlayerViewer
import cc.trixey.invero.ui.common.Panel
import org.bukkit.entity.Player
import java.util.concurrent.ConcurrentHashMap

/**
 * Invero
 * cc.trixey.invero.core.Context
 *
 * @author Arasple
 * @since 2023/1/17 11:14
 */
class Context(
    val viewer: PlayerViewer,
    val session: Session? = null,
    val panel: Panel? = null,
    val icon: IconElement? = null,
    val extVars: Map<String, Any> = emptyMap()
) {

    constructor(session: Session) : this(session.viewer, session)

    fun parse(input: String) = session?.parse(input, this) ?: player?.let { input.translateFormattedMessage(it, variables) } ?: input

    fun parse(input: List<String>): List<String> {
        return input.map { parse(it) }
    }

    val player: Player?
        get() = viewer.get()

    val menu: BaseMenu?
        get() = session?.menu as? BaseMenu

    val contextVariables: ConcurrentHashMap<String, Any> = buildMap {
        icon?.let { put("@icon", it) }
        menu?.let { put("@menu", it) }
        panel?.let { put("@panel", it) }
        put("@context", this@Context)
        this += extVars
    }.let { ConcurrentHashMap(it) }

    val variables: Map<String, Any>
        get() = session?.getVariables(contextVariables) ?: contextVariables

}