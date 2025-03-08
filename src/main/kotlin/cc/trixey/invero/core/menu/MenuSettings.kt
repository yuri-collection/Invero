package cc.trixey.invero.core.menu

import cc.trixey.invero.core.serialize.InventoryTypeSerializer
import cc.trixey.invero.core.serialize.MenuTitleSerializer
import cc.trixey.invero.ui.common.ContainerType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.event.inventory.InventoryType
import taboolib.common5.Baffle
import java.util.concurrent.TimeUnit

/**
 * Invero
 * cc.trixey.invero.core.menu.MenuSettings
 *
 * @author Arasple
 * @since 2023/1/25 11:35
 */
@Serializable
class MenuSettings(
    @Serializable(with = MenuTitleSerializer::class)
    val title: MenuTitle,
    var rows: Int?,
    val virtual: Boolean = false,
    @Serializable(with = InventoryTypeSerializer::class)
    val type: InventoryType = InventoryType.CHEST,
    @SerialName("hide-player-inventory")
    val hidePlayerInventory: Boolean = false,
    @SerialName("override-player-inventory")
    val overridePlayerInventory: Boolean = true,
    @SerialName("min-interact-interval")
    val minInteractInterval: Long = 2_00
) {

    val interactBaffle by lazy {
        Baffle.of(minInteractInterval, TimeUnit.MILLISECONDS)
    }

    val containerType by lazy {
        if (type != InventoryType.CHEST) ContainerType.fromBukkitType(type.name)
        else ContainerType.ofRows(rows ?: 6)
    }

}