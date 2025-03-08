package cc.trixey.invero.core.api.event

import cc.trixey.invero.common.ElementGenerator
import taboolib.platform.type.BukkitProxyEvent

/**
 * Invero_Deprecated
 * cc.trixey.invero.core.api.event.ElementGeneratorInitEvent
 *
 * @author Arasple
 * @since 2023/8/21 17:28
 */
class ElementGeneratorInitEvent(val identifier: String, val instance: ElementGenerator) : BukkitProxyEvent()