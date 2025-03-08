package cc.trixey.invero.common.api

import org.bukkit.entity.Player
import taboolib.expansion.DataContainer
import java.util.*

/**
 * Invero
 * cc.trixey.invero.common.api.InveroDataManager
 *
 * @author Arasple
 * @since 2023/2/1 17:08
 */
interface InveroDataManager {

    fun getDatabaseType(): Type

    fun getGlobalData(): DataContainer

    fun getPlayerData(player: Player): DataContainer

    fun getPlayerData(player: UUID): DataContainer

    enum class Type {

        SQLITE,

        SQL

    }

}