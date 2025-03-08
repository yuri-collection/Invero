@file:OptIn(ExperimentalSerializationApi::class)

package cc.trixey.invero.core.item

import cc.trixey.invero.core.animation.CycleMode
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

/**
 * Invero
 * cc.trixey.invero.core.item.FrameProperties
 *
 * @author Arasple
 * @since 2023/1/31 15:52
 */
@Serializable
class FrameProperties(
    @SerialName("delay")
    @JsonNames("default-delay", "period", "last")
    val defaultDelay: Long = 20,
    @SerialName("mode")
    @JsonNames("type")
    val frameMode: CycleMode = CycleMode.LOOP
)