package cc.trixey.invero.core

import cc.trixey.invero.core.serialize.SelectorAgentPanel
import cc.trixey.invero.ui.bukkit.PanelContainer
import cc.trixey.invero.ui.bukkit.api.dsl.firstAvailablePositionForPanel
import cc.trixey.invero.ui.common.Panel
import cc.trixey.invero.ui.common.Pos
import cc.trixey.invero.ui.common.Scale
import kotlinx.serialization.Serializable

/**
 * Invero
 * cc.trixey.invero.core.MenuPanels
 *
 * @author Arasple
 * @since 2023/1/15 22:35
 */
@Serializable(with = SelectorAgentPanel::class)
abstract class AgentPanel {

    abstract val scale: Scale

    abstract val layout: Layout?

    abstract val locate: Pos?

    abstract fun invoke(parent: PanelContainer, session: Session): Panel

    fun PanelContainer.locate(): Pair<Int, Int> {
        return locate?.value ?: firstAvailablePositionForPanel()
    }

    fun requireBukkitWindow(): Boolean {
        return false
    }

}