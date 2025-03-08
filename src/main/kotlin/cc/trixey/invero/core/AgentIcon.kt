package cc.trixey.invero.core

import cc.trixey.invero.core.icon.IconElement
import cc.trixey.invero.ui.common.Panel

/**
 * Invero
 * cc.trixey.invero.core.IconAgent
 *
 * @author Arasple
 * @since 2023/1/16 9:54
 */
abstract class AgentIcon {

    abstract val id: String?

    abstract fun invoke(
        session: Session,
        agent: AgentPanel,
        panel: Panel,
        vars: Map<String, Any> = emptyMap(),
        renderNow: Boolean = true,
    ): IconElement

}