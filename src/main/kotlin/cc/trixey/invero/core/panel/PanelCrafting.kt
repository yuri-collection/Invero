@file:OptIn(ExperimentalSerializationApi::class)

package cc.trixey.invero.core.panel

import cc.trixey.invero.core.AgentPanel
import cc.trixey.invero.core.Context
import cc.trixey.invero.core.Layout
import cc.trixey.invero.core.Session
import cc.trixey.invero.core.icon.Icon
import cc.trixey.invero.core.serialize.MappedIconSerializer
import cc.trixey.invero.core.serialize.PosSerializer
import cc.trixey.invero.core.serialize.ScaleSerializer
import cc.trixey.invero.ui.bukkit.PanelContainer
import cc.trixey.invero.ui.bukkit.api.dsl.craftingIOPanel
import cc.trixey.invero.ui.common.Pos
import cc.trixey.invero.ui.common.Scale
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.JsonNames

/**
 * Invero
 * cc.trixey.invero.core.panel.PanelCrafting
 *
 * @author Arasple
 * @since 2023/2/10 15:40
 */
@Serializable
class PanelCrafting(
    @Serializable(with = ScaleSerializer::class)
    @SerialName("scale")
    private val _scale: Scale?,
    override val layout: Layout?,
    @Serializable(with = PosSerializer::class)
    override val locate: Pos?,
    @JsonNames("icon", "item", "items")
    @Serializable(with = MappedIconSerializer::class)
    override val icons: Map<String, Icon>,
    val crafting: StructureCrafting
) : AgentPanel(), IconContainer {

    @Transient
    override val scale = run {
        require(layout != null) { "PanelScroll must specifiy a layout" }
        _scale ?: layout.getScale()
    }

    init {
        registerIcons()
    }

    override fun invoke(parent: PanelContainer, session: Session) =
        parent.craftingIOPanel(scale.pair, parent.locate()) {
            // def icons
            icons.forEach { (_, icon) -> icon.invoke(session, this@PanelCrafting, this@craftingIOPanel) }
            layout?.search(" ")?.forEach { free(it.slot) }

            listener {
                crafting.listener?.run(
                    context = session.getVariable("@context") as? Context? ?: Context(session.viewer, session)
                )
            }
        }

}